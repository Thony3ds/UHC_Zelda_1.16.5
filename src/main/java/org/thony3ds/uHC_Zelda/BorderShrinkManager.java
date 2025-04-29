package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class BorderShrinkManager {

    private final World world;
    private final BossBar bossBar;
    private final int initialSize;
    private final int finalSize;
    private final long durationTicks;
    private long ticksElapsed = 0;
    private BukkitTask task;

    public BorderShrinkManager(World world, int initialSize, int finalSize, long durationSeconds) {
        this.world = world;
        this.initialSize = initialSize;
        this.finalSize = finalSize;
        this.durationTicks = durationSeconds * 20;

        this.bossBar = Bukkit.createBossBar("Réduction de la bordure", BarColor.RED, BarStyle.SOLID);
        this.bossBar.setVisible(true);
        for (Player player : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(player);
        }
    }

    public void start() {
        world.getWorldBorder().setSize(finalSize, durationTicks / 20);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                ticksElapsed++;

                double progress = 1.0 - ((double) ticksElapsed / durationTicks);
                bossBar.setProgress(Math.max(0, progress));

                if (ticksElapsed >= durationTicks) {
                    bossBar.removeAll();
                    cancel();
                }
            }
        }.runTaskTimer(UHC_Zelda.getInstance(), 0L, 1L);
    }
}