package org.thony3ds.uHC_Zelda;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.thony3ds.uHC_Zelda.basicItem.CraftItems;
import org.thony3ds.uHC_Zelda.basicItem.ItemManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VictoryManager implements Listener {

    private final Plugin plugin;
    private final int WIN_RADIUS = 20;
    private final int WIN_DURATION = 5 * 60; // en secondes
    private boolean isVictoryPhaseRunning = false;
    private Player currentCandidate = null;
    private BukkitRunnable timerTask = null;

    public VictoryManager(Plugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void checkForVictoryStart() {
        if (isVictoryPhaseRunning) return;

        Player triforceHolder = getTriforceHolder();
        if (triforceHolder != null && isInSacredZone(triforceHolder)) {
            startVictoryTimer(triforceHolder);
        }
    }

    private Player getTriforceHolder() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (hasAllTriforces(player)) {
                return player;
            }
        }
        return null;
    }

    private boolean hasAllTriforces(Player player) {
        Set<String> required = new HashSet<>();
        required.add("Triforce du Courage");
        required.add("Triforce de la Force");
        required.add("Triforce de la Sagesse");

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
                required.remove(item.getItemMeta().getDisplayName());
            }
        }

        return required.isEmpty();
    }

    private boolean isInSacredZone(Player player) {
        Location loc = player.getLocation();
        return loc.getWorld() != null &&
                loc.getWorld().getName().equals("world") &&
                loc.distance(new Location(loc.getWorld(), 0, loc.getY(), 0)) <= WIN_RADIUS;
    }

    private void startVictoryTimer(Player player) {
        isVictoryPhaseRunning = true;
        currentCandidate = player;

        Bukkit.broadcastMessage(player.getName() + " assemble la Triforce !");

        timerTask = new BukkitRunnable() {
            int timeLeft = WIN_DURATION;

            @Override
            public void run() {
                if (player.isDead() || !isInSacredZone(player)) {
                    player.sendMessage("Tu as quitté la zone ou tu es mort. Le rituel est annulé.");
                    cancelVictory();
                    return;
                }

                if (timeLeft % 60 == 0 || timeLeft <= 10) {
                    player.sendMessage("Temps restant avant la victoire : " + timeLeft + " secondes.");
                }

                if (timeLeft <= 0) {
                    Bukkit.broadcastMessage(player.getName() + " a assemblé la Triforce !");
                    spawnBigVictoryParticles(player);
                    teleportEveryoneToWinner(player);
                    cancelVictory();
                    ItemManager itemManager = new ItemManager();
                    player.getInventory().removeItem(itemManager.getItemByName("triforce_courage"));
                    player.getInventory().removeItem(itemManager.getItemByName("triforce_force"));
                    player.getInventory().removeItem(itemManager.getItemByName("triforce_sagesse"));
                    return;
                }

                timeLeft--;
            }
        };

        timerTask.runTaskTimer(plugin, 0, 20);
    }

    private void cancelVictory() {
        isVictoryPhaseRunning = false;
        currentCandidate = null;
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (!isVictoryPhaseRunning) return;
        if (!event.getPlayer().equals(currentCandidate)) return;

        if (!isInSacredZone(event.getPlayer())) {
            event.getPlayer().sendMessage("❌ Tu as quitté la zone sacrée !");
            cancelVictory();
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (event.getEntity().equals(currentCandidate)) {
            cancelVictory();
            Bukkit.broadcastMessage("Le porteur des Triforce est mort !");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (event.getPlayer().equals(currentCandidate)) {
            cancelVictory();
        }
    }

    public void spawnLiteVictoryParticles(Player winner){
        Location loc = winner.getLocation();
        World world = loc.getWorld();

        new BukkitRunnable(){
            double y =0;
            @Override
            public void run(){
                if(y>=2.5){
                    cancel();
                    return;
                }

                double radius = 1.5 - (y/2.5); // Spirale qui se resserre
                for (double angle = 0; angle < Math.PI*2;angle+= Math.PI / 8){
                    double x = Math.cos(angle)*radius;
                    double z = Math.sin(angle)*radius;
                    Location particleLoc = loc.clone().add(x, y, z);

                    world.spawnParticle(Particle.ENCHANTMENT_TABLE, particleLoc, 0, 0, 0.1, 0, 1);
                }

                y += 0.1;
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    public void spawnBigVictoryParticles(Player winner) {
        Location center = winner.getLocation().add(0, 1, 0);
        double startingRadius = 6.0;
        int points = 75;

        World world = center.getWorld();

        new BukkitRunnable() {
            double currentRadius = startingRadius;
            int ticks = 0;

            @Override
            public void run() {
                if (currentRadius <= 0.5 || ticks >= 100) {
                    this.cancel();
                    return;
                }

                for (int i = 0; i < points; i++) {
                    double angle = 2 * Math.PI * i / points;
                    double x = center.getX() + currentRadius * Math.cos(angle);
                    double z = center.getZ() + currentRadius * Math.sin(angle);

                    double height = 0.5 + (i%10)*0.2; // Environ + 2 blocs
                    double y = center.getY() + height;

                    Location particleLoc = new Location(world, x, y, z);
                    world.spawnParticle(Particle.ENCHANTMENT_TABLE, particleLoc, 0, 0, 0, 0, 0);
                }

                currentRadius -= 0.1; // Se referme progressivement
                ticks++;
            }
        }.runTaskTimer(plugin, 0L, 2L);
    }

    public void teleportEveryoneToWinner(Player winner){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String winnerTeam = getScoreboardLine(winner, 3);
        Location winLoc = winner.getLocation();

        for (Player p: Bukkit.getOnlinePlayers()){
            p.teleport(winLoc);

            String playerTeam = getScoreboardLine(p, 3);
            if (playerTeam == null || !playerTeam.equals(winnerTeam)){
                p.setGameMode(GameMode.SPECTATOR);
            }else{
                p.setGameMode(GameMode.SURVIVAL);
            }
        }
    }
    private String getScoreboardLine(Player player, int index){
        if (player.getScoreboard() == null) return "";
        Objective obj = player.getScoreboard().getObjective(DisplaySlot.SIDEBAR);
        if (obj == null)return "";

        List<String> lines = new ArrayList<>();
        for (String entry: player.getScoreboard().getEntries()){
            Score score = obj.getScore(entry);
            lines.add(entry);
        }

        lines.sort((a, b) -> Integer.compare(
                obj.getScore(b).getScore(),
                obj.getScore(a).getScore()
        ));

        return index < lines.size() ? ChatColor.stripColor(lines.get(index)) : "";
    }
}
