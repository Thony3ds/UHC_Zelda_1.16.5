package org.thony3ds.uHC_Zelda;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class Uz_get_item implements CommandExecutor {
    private ItemManager itemManager = new ItemManager();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.hasPermission("uhc.op")) {
            commandSender.sendMessage("Vous n'avez pas la permission d'exécuter cette commande !");
            return true;
        }
        if (!(commandSender instanceof Player)){
            commandSender.sendMessage("Vous devez être un joueur !");
        }
        if (strings.length <1){
            commandSender.sendMessage("Usage: /uz_getItem item_name");
        }

        Player player = (Player) commandSender;

        player.getInventory().addItem(itemManager.getItemByName(strings[0]));

        return true;
    }
}
