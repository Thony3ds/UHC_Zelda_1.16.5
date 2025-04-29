package org.thony3ds.uHC_Zelda.basicItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemManager {

    private CraftItems craftItems = new CraftItems();

    public void setItemInChest(World world, int x, int y, int z, ItemStack item){
        Block block = world.getBlockAt(x, y, z);

        if (block.getState() instanceof Chest){
            Chest chest = (Chest) block.getState();
            chest.getBlockInventory().addItem(item);
        }else{
            Bukkit.getLogger().warning("Le bloc en "+x+";"+y+";"+z+" n'est pas un coffre !");
        }
    }

    public ItemStack getItemByName(String name){
        ItemStack item = null;
        ItemMeta meta = null;
        switch (name.toLowerCase()){
            case "rubis":
                item = craftItems.getRubis(1);
                break;
            case "triforce_courage":
                item = craftItems.getTriforceCourage();
                break;
            case "triforce_force":
                item = craftItems.getTriforceForce();
                break;
            case "triforce_sagesse":
                item = craftItems.getTriforceSagesse();
                break;
            case "triforce_compass":
                item = craftItems.getBoussoleTriforce();
                break;

            default:
                item = new ItemStack(Material.DIAMOND_SWORD);
                meta = item.getItemMeta();
                meta.setDisplayName("Error :(");
                item.setItemMeta(meta);
                break;
        }

        return item;
    }
}
