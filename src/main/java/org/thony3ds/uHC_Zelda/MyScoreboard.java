package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class MyScoreboard {

    private UHC_Zelda plugin = UHC_Zelda.getInstance();

    public static void createScoreboard(Player player){
        MyScoreboard.updateScoreboard(player);
    }
    public static void updateScoreboard(Player player){
        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = board.registerNewObjective("uhc", "dummy", ChatColor.GOLD + "UHC Zelda");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        Joueur info = PlayerManager.joueurs.get(player);
        String equipe = info != null ? info.getEquipe() : "Aucune";
        String classe = info != null ? info.getClasse() : "Aucune";

        Score spacer1 = obj.getScore(" ");
        spacer1.setScore(7);

        Score episode = obj.getScore(ChatColor.GREEN + "Episode: "+ChatColor.WHITE + UHC_Zelda.episode);
        episode.setScore(6);

        Score timer = obj.getScore(ChatColor.GREEN + "Temps: "+ ChatColor.WHITE + formatTime(UHC_Zelda.seconds));
        timer.setScore(5);

        Score teamColor = obj.getScore(ChatColor.GREEN + "Equipe: "+ ChatColor.WHITE + equipe);
        teamColor.setScore(4);

        Score playerClass = obj.getScore(ChatColor.GREEN + "Classe: "+ChatColor.WHITE + classe);
        playerClass.setScore(3);

        Score spacer2 = obj.getScore(" ");
        spacer2.setScore(2);

        Score author = obj.getScore(ChatColor.GOLD + "Made by Thony3ds V"+UHC_Zelda.version);
        author.setScore(1);

        player.setScoreboard(board);
    }

    private static String formatTime(int totalSeconds){
        int min = totalSeconds /60;
        int sec = totalSeconds % 60;
        return String.format("%02d/%02d", min, sec);
    }
}
