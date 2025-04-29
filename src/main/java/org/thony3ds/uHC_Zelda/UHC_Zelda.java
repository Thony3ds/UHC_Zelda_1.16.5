package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.thony3ds.uHC_Zelda.basicItem.CraftItems;
import org.thony3ds.uHC_Zelda.basicItem.ItemListener;

import java.util.Objects;

public final class UHC_Zelda extends JavaPlugin implements Listener {

    public static String version = "1.0-SNAPSHOT";

    //Init Classes
    public static UHC_Zelda instance;
    public MyScoreboard myScoreboard = new MyScoreboard();
    public Uz_start uzStart = new Uz_start();
    public Uz_class uzClass = new Uz_class();
    public Uz_team uzTeam = new Uz_team();
    public PlayerManager playerManager = new PlayerManager();
    public CraftItems craftItems = new CraftItems();
    public Uz_get_item uz_get_item = new Uz_get_item();
    public ItemListener itemListener = new ItemListener();
    public TriforceTracker triforceTracker = new TriforceTracker();
    public static VictoryManager victoryManager;

    // Init vars
    public static int seconds = 0;
    public static int episode = 1;

    @Override
    public void onEnable() {
        instance = this;
        craftItems.Init_Crafts();
        getLogger().info("Plugin Activé ! Ne pas oublier d'activer le nether off et gamerule naturalRegeneration false :)");
        //Init command
        Objects.requireNonNull(getCommand("uz_start")).setExecutor(uzStart);
        Objects.requireNonNull(getCommand("uz_class")).setExecutor(uzClass);
        Objects.requireNonNull(getCommand("uz_team")).setExecutor(uzTeam);
        Objects.requireNonNull(getCommand("uz_get_item")).setExecutor(uz_get_item);
        //Init Listeners
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(itemListener, this);
        getServer().getPluginManager().registerEvents(triforceTracker, this);
        //Init World
        World world = Bukkit.getWorlds().get(0);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setCenter(0, 0);
        worldBorder.setSize(2000);
        worldBorder.setDamageBuffer(5);
        worldBorder.setDamageAmount(1);
        Bukkit.getWorlds().get(0).setPVP(false);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        myScoreboard.createScoreboard(event.getPlayer());
        Joueur joueur = new Joueur("", "");
        PlayerManager.joueurs.put(event.getPlayer(), joueur);
    }

    public static UHC_Zelda getInstance(){
        return instance;
    }
}
