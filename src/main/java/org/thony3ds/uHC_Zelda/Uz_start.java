package org.thony3ds.uHC_Zelda;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thony3ds.uHC_Zelda.basicItem.ItemManager;

import java.util.Objects;
import java.util.Random;

public final class Uz_start implements CommandExecutor {

    private final UHC_Zelda plugin = UHC_Zelda.getInstance();
    private ItemManager itemManager = new ItemManager();
    private TriforceTracker triforceTracker = new TriforceTracker();
    public int secondes;
    public Location courageLoc;
    public Location forceLoc;
    public Location sagesseLoc;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("uhc.op")) {
            commandSender.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
            return true;
        }

        commandSender.sendMessage("Lancement de l'UHC...");

        World world = Bukkit.getWorlds().get(0);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(0, 0);
        worldBorder.setSize(2001);
        worldBorder.setDamageBuffer(5);
        worldBorder.setDamageAmount(1);

        world.setTime(0);
        world.setStorm(false);

        ExternalPlugins.pasteSchematic(new Location(world, 0, world.getHighestBlockYAt(0, 0), 0), "temple_du_temps");
        //TODO Random structures + effet quand on approche
        ExternalPlugins.pasteSchematic(new Location(world, 500, world.getHighestBlockYAt(500, 500), 500), "temple_du_temps");
        ExternalPlugins.pasteSchematic(new Location(world, 500, world.getHighestBlockYAt(500, -500), -500), "temple_du_temps");
        ExternalPlugins.pasteSchematic(new Location(world, -500, world.getHighestBlockYAt(-500, 500), 500), "temple_du_temps");
        ExternalPlugins.pasteSchematic(new Location(world, -500, world.getHighestBlockYAt(-500, -500), -500), "temple_du_temps");
        // Generate Triforces structures
        courageLoc = randomLoc(world);
        forceLoc = randomLoc(world);
        sagesseLoc = randomLoc(world);
        ExternalPlugins.pasteSchematic(courageLoc, "temple_du_temps");
        ExternalPlugins.pasteSchematic(forceLoc, "temple_du_temps");
        ExternalPlugins.pasteSchematic(sagesseLoc, "temple_du_temps");

        commandSender.sendMessage("Triforces Générées !");

        new BukkitRunnable() {
            @Override
            public void run() {
                secondes++;

                UHC_Zelda.seconds = secondes;
                if (secondes == 30) {
                    Bukkit.broadcastMessage("Le PVP est maintenant activé !");
                    Bukkit.getWorlds().get(0).setPVP(true);
                    UHC_Zelda.episode++;
                    String subTitle = "PVP et Structures activés !"; //TODO items in structures

                    for (Player player: Bukkit.getOnlinePlayers()){
                        player.sendTitle("Episode 2", subTitle, 30, 50, 40);
                    }
                }else if (secondes == 60){
                    Bukkit.broadcastMessage("Les Triforces sont apparus !");
                    UHC_Zelda.episode++;
                    String subTitle = "Les Triforces sont apparus !"; // TODO remove chest
                    itemManager.setItemInChest(Bukkit.getWorlds().get(0),3,63,3,itemManager.getItemByName("triforce_courage"));
                    itemManager.setItemInChest(Bukkit.getWorlds().get(0),3,63,3,itemManager.getItemByName("triforce_force"));
                    itemManager.setItemInChest(Bukkit.getWorlds().get(0),3,63,3,itemManager.getItemByName("triforce_sagesse"));
                    Location triforceCourageLoc = new Location(Bukkit.getWorlds().get(0), 3, 63, 3);
                    TriforceTracker.setLocation("Triforce du Courage", triforceCourageLoc);
                    Location triforceForceLoc = new Location(Bukkit.getWorlds().get(0), 3, 63, 3);
                    TriforceTracker.setLocation("Triforce de la Forge", triforceForceLoc);
                    Location triforceSagesseLoc = new Location(Bukkit.getWorlds().get(0), 3, 63, 3);
                    TriforceTracker.setLocation("Triforce de la Sagesse", triforceSagesseLoc);

                    UHC_Zelda.victoryManager = new VictoryManager(UHC_Zelda.instance);

                    new BukkitRunnable(){
                        @Override
                        public void run(){
                            UHC_Zelda.victoryManager.checkForVictoryStart();
                        }
                    }.runTaskTimer(UHC_Zelda.instance, 0L, 100L);

                    for (Player player: Bukkit.getOnlinePlayers()){
                        player.sendTitle("Episode 3", subTitle, 30, 50, 40);
                    }
                }else if (secondes == 90){
                    Bukkit.broadcastMessage("Rétrecicement de l'anneau !");
                    UHC_Zelda.episode++;
                    String subTitle = "Le monde s'écroule !";

                    double targetSize = 100.0;
                    World world = Bukkit.getWorlds().get(0);
                    WorldBorder border = world.getWorldBorder();
                    long time = (long) (border.getSize()-targetSize);

                    border.setSize(targetSize, time);
                    BorderShrinkManager shrink = new BorderShrinkManager(
                            Bukkit.getWorlds().get(0), 2001, 101, 1900
                    );
                    shrink.start();

                    for (Player player: Bukkit.getOnlinePlayers()){
                        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
                        player.sendTitle("Episode 3", subTitle, 30, 50, 40);
                    }
                }

                for (Player player : Bukkit.getOnlinePlayers()){
                    MyScoreboard.updateScoreboard(player);
                }

            }
        }.runTaskTimer(UHC_Zelda.instance, 20L, 20L);

        return true;
    }
    private Location randomLoc(World world){
        Random random = new Random();
        int borderLimit = 950;

        int x = random.nextInt(borderLimit*2)-borderLimit;
        int z = random.nextInt(borderLimit*2)-borderLimit;
        int y = 5 + random.nextInt(26);

        Material blockType = world.getBlockAt(x, y-1, z).getType();
        if (blockType == Material.WATER || blockType == Material.LAVA){
            return randomLoc(world);
        }

        return new Location(world, x, y, z);
    }
}
