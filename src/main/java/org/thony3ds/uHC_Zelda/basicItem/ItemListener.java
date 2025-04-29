package org.thony3ds.uHC_Zelda.basicItem;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.thony3ds.uHC_Zelda.TriforceTracker;

public final class ItemListener implements Listener {
    private ItemManager itemManager = new ItemManager();
    private TriforceTracker triforceTracker = new TriforceTracker();

    @EventHandler
    public void onCompassLeftClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK){
            return;
        }

        if (item.getType() == Material.COMPASS && item.getItemMeta()!=null
                && item.getItemMeta().hasDisplayName()
                && ChatColor.stripColor(item.getItemMeta().getDisplayName()).startsWith("Boussole de Triforce")){

            String tri = "";

            switch (item.getItemMeta().getDisplayName()){
                case "Boussole de Triforce1":
                    tri = "Triforce du Courage";
                    break;
                case "Boussole de Triforce2":
                    tri = "Triforce de la Force";
                    break;
                case "Boussole de Triforce3":
                    tri = "Triforce de la Sagesse";
                    break;
            }

            Location triforceLocation = TriforceTracker.getLocation(tri);
            double distance = player.getLocation().distance(triforceLocation);
            if (tri == "Triforce du Courage") {
                player.sendMessage(ChatColor.GREEN + "La Triforce Courage est à " + (int) distance + " blocs de toi !");
            }else if (tri == "Triforce de la Force"){
                player.sendMessage(ChatColor.RED + "La Triforce Force est à " + (int) distance + " blocs de toi !");
            }else{
                player.sendMessage(ChatColor.BLUE + "La Triforce Sagesse est à " + (int) distance + " blocs de toi !");
            }
        }
    }
    @EventHandler
    public void onRightClickCompass(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item == null || event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK){
            return;
        }

        if (item.getType() == Material.COMPASS && item.getItemMeta()!=null
                && item.getItemMeta().hasDisplayName()
                && ChatColor.stripColor(item.getItemMeta().getDisplayName()).startsWith("Boussole de Triforce")){

            ItemMeta meta = item.getItemMeta();
            switch (item.getItemMeta().getDisplayName()){
                case "Boussole de Triforce1":
                    meta.setDisplayName("Boussole de Triforce2");
                    break;
                case "Boussole de Triforce2":
                    meta.setDisplayName("Boussole de Triforce3");
                    break;
                case "Boussole de Triforce3":
                    meta.setDisplayName("Boussole de Triforce1");
                    break;
            }
            item.setItemMeta(meta);
        }
    }

    @EventHandler
    public void onGrassBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType() == Material.TALL_GRASS || block.getType() == Material.GRASS){
            player.getInventory().addItem(itemManager.getItemByName("rubis"));
        }
    }
}
