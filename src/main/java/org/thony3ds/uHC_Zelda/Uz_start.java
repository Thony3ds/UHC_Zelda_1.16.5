package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public final class Uz_start implements CommandExecutor {

    private final UHC_Zelda plugin = UHC_Zelda.getInstance();
    public int secondes;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("uhc.zelda.start")) {
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
                }

                for (Player player : Bukkit.getOnlinePlayers()){
                    MyScoreboard.updateScoreboard(player);
                }

            }
        }.runTaskTimer(UHC_Zelda.instance, 20L, 20L);

        return true;
    }
}
