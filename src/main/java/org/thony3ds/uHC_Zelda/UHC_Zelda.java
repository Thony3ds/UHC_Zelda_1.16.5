package org.thony3ds.uHC_Zelda;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class UHC_Zelda extends JavaPlugin {

    public Uz_start uzStart = new Uz_start();

    @Override
    public void onEnable() {
        getLogger().info("Plugin Activé !");
        Objects.requireNonNull(getCommand("uz_start")).setExecutor(uzStart);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
