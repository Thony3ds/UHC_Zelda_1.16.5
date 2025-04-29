package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public final class TriforceTracker implements Listener {
    public static final Set<String> TRIFORCE_NAMES = Set.of("Triforce du Courage", "Triforce de la Force", "Triforce de la Sagesse");

    private static final Map<String, UUID> triforceHolders = new HashMap<>();
    private static final Map<String, Location> triforceLocations = new HashMap<>();

    public static void setHolder(String triforceName, UUID playerUUID) {
        triforceHolders.put(triforceName, playerUUID);
        triforceLocations.remove(triforceName);
    }

    public static void setLocation(String triforceName, Location location) {
        triforceLocations.put(triforceName, location);
        triforceHolders.remove(triforceName);
    }

    public static UUID getHolder(String triforceName) {
        return triforceHolders.get(triforceName);
    }

    public static Location getLocation(String triforceName) {
        return triforceLocations.get(triforceName);
    }

    public static boolean isHeld(String triforceName) {
        return triforceHolders.containsKey(triforceName);
    }

    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {
        ItemStack item = event.getItem().getItemStack();
        if (item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) {
            String name = item.getItemMeta().getDisplayName();
            if (TriforceTracker.TRIFORCE_NAMES.contains(name) && event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                TriforceTracker.setHolder(name, player.getUniqueId());
                Bukkit.broadcastMessage(ChatColor.GOLD + "Une triforce a était trouvée !");
            }
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        if (item.hasItemMeta() && Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) {
            String name = item.getItemMeta().getDisplayName();
            if (TriforceTracker.TRIFORCE_NAMES.contains(name)) {
                TriforceTracker.setLocation(name, event.getItemDrop().getLocation());
                Bukkit.broadcastMessage(ChatColor.GOLD + "Une triforce est tombée !");
            }
        }
    }
    @EventHandler
    public void onItemDespawn(ItemDespawnEvent event){
        Item itemEntity = event.getEntity();
        if (itemEntity.getItemStack().hasItemMeta() && Objects.requireNonNull(itemEntity.getItemStack().getItemMeta()).hasDisplayName()) {
            String name = itemEntity.getItemStack().getItemMeta().getDisplayName();
            if (TriforceTracker.TRIFORCE_NAMES.contains(name)) {
                event.setCancelled(true);
            }
        }
    }
}