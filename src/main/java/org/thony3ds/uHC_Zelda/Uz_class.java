package org.thony3ds.uHC_Zelda;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public final class Uz_class implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)){
            return false;
        }
        Player player = (Player) commandSender;

        if (args.length <1){
            player.sendMessage("Usage: /uz_class classe");
        }

        Joueur joueur= PlayerManager.joueurs.get(player);
        if (joueur != null && joueur.getClasse() != null && !(Objects.equals(joueur.getClasse(), ""))){
            player.sendMessage(ChatColor.RED + "Tu as déjà choisie ta classe: "+joueur.getClasse());
            return true;
        }

        String classe = args[0].toLowerCase();

        switch (classe){
            case "piaf":
                if ( joueur != null) {
                    joueur = new Joueur(classe, joueur.getEquipe());

                    player.sendMessage(ChatColor.GREEN + "Classe choisie: Piaf");
                    break;
                }

            case "gorron":
                if ( joueur != null) {
                    joueur = new Joueur(classe, joueur.getEquipe());

                    player.sendMessage(ChatColor.GREEN + "Classe choisie: Gorron");
                    break;
                }

            default:
                player.sendMessage(ChatColor.RED + "Nom de classe invalide !");
                return false;
        }
        PlayerManager.joueurs.put(player, joueur);
        return true;
    }
}
