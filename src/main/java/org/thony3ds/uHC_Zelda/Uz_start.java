package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.thony3ds.uHC_Zelda.basicItem.ItemManager;

import java.util.Objects;

public final class Uz_start implements CommandExecutor {

    private final UHC_Zelda plugin = UHC_Zelda.getInstance();
    private ItemManager itemManager = new ItemManager();
    private TriforceTracker triforceTracker = new TriforceTracker();
    public int secondes;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("uhc.op")) {
            commandSender.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
            return true;
        }

        commandSender.sendMessage("Lancement de l'UHC...");

        new BukkitRunnable() {
            @Override
            public void run() {
                secondes++;

                UHC_Zelda.seconds = secondes;
                if (secondes == 30) {
                    Bukkit.broadcastMessage("Le PVP est maintenant activé !");
                    Bukkit.getWorlds().get(0).setPVP(true);
                    UHC_Zelda.episode++;
                    String subTitle = "PVP et Structures activés !";

                    for (Player player: Bukkit.getOnlinePlayers()){
                        player.sendTitle("Episode 2", subTitle, 30, 50, 40);
                    }
                }else if (secondes == 60){
                    Bukkit.broadcastMessage("Les Triforces sont apparus !");
                    UHC_Zelda.episode++;
                    String subTitle = "Les Triforces sont apparus !";
                    itemManager.setItemInChest(Bukkit.getWorlds().get(0),3,63,3,itemManager.getItemByName("triforce_courage"));
                    itemManager.setItemInChest(Bukkit.getWorlds().get(0),3,63,3,itemManager.getItemByName("triforce_force"));
                    itemManager.setItemInChest(Bukkit.getWorlds().get(0),3,63,3,itemManager.getItemByName("triforce_sagesse"));
                    Location triforceCourageLoc = new Location(Bukkit.getWorlds().get(0), 3, 63, 3);
                    TriforceTracker.setLocation("Triforce du Courage", triforceCourageLoc);
                    Location triforceForceLoc = new Location(Bukkit.getWorlds().get(0), 3, 63, 3);
                    TriforceTracker.setLocation("Triforce de la Forge", triforceForceLoc);
                    Location triforceSagesseLoc = new Location(Bukkit.getWorlds().get(0), 3, 63, 3);
                    TriforceTracker.setLocation("Triforce de la Sagesse", triforceSagesseLoc);

                    for (Player player: Bukkit.getOnlinePlayers()){
                        player.sendTitle("Episode 3", subTitle, 30, 50, 40);
                    }
                }else if (secondes == 90){
                    Bukkit.broadcastMessage("Rétrecicement de l'anneau !");
                    UHC_Zelda.episode++;
                    String subTitle = "Le monde s'écroule !"; //TODO

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

        World world = Bukkit.getWorlds().get(0);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(0, 0);
        worldBorder.setSize(2001);
        worldBorder.setDamageBuffer(5);
        worldBorder.setDamageAmount(1);

        world.setTime(0);
        world.setStorm(false);

        ExternalPlugins.pasteSchematic(new Location(world, 0, world.getHighestBlockYAt(0, 0), 0), "temple_du_temps");

        return true;
    }
}
