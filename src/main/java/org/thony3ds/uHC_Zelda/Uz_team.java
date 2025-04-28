package org.thony3ds.uHC_Zelda;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class Uz_team implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;

        if (args.length <1){
            player.sendMessage("Usage: /uz_team equipe");
        }

        Joueur joueur= PlayerManager.joueurs.get(player);
        if (joueur != null && joueur.getEquipe() != null && !(Objects.equals(joueur.getEquipe(), ""))){
            player.sendMessage(ChatColor.RED + "Tu as déjà choisie ton équipe: "+joueur.getClasse());
            return true;
        }

        String equipe = args[0].toLowerCase();

        if (joueur != null  && equipe.length() > 2){
            joueur = new Joueur(joueur.getClasse(), equipe);

            player.sendMessage(ChatColor.GREEN + "Tu as choisie l'equipe: "+equipe);
        }

        PlayerManager.joueurs.put(player, joueur);
        return true;
    }
}
