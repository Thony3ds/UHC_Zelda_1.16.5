package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public final class Uz_gen_dj implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("uhc.op")) {
            commandSender.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
            return true;
        }
        if (!(args.length == 2)){
            commandSender.sendMessage("Usage: /uz_gen_dj location donjon_name");
        }
        int x,z = 0;
        switch (args[0]){
            case "1":
                x = 500;
                z = 500;
                break;
            case "2":
                x = -500;
                z = 500;
                break;
            case "3":
                x = 500;
                z = -500;
                break;
            case "4":
                x = -500;
                z = -500;
                break;
            default:
                commandSender.sendMessage("Warning Unknow location: "+ args[0]);
                return false;
        }
        String dj = "";
        switch (args[1]){
            case "1":
                dj = "Arbre_Mojo";
                break;
            case "2":
                dj = "Volcan_Ordin";
            case "3":
                dj = "Lac_Hylia";
                break;
            case "4":
                dj = "Forteresse_Gerudo";
                break;
            default:
                commandSender.sendMessage("Warning Unknow donjon: "+ args[1]);
                return false;
        }
        Location loc = new Location(Bukkit.getWorlds().get(0), x, getTempleGenCoordsY(Bukkit.getWorlds().get(0), x, z, dj),z);
        commandSender.sendMessage("Generation...");
        ExternalPlugins.pasteSchematic(loc, dj);
        commandSender.sendMessage("Structure creee !");

        return true;
    }
    private int getTempleGenCoordsY(World world, int x, int z, String temple){
        if (temple == "Lac_Hylia"){
            return world.getHighestBlockYAt(x, z) - 50;
        }else{
            return world.getHighestBlockYAt(x, z);
        }
    }
}
