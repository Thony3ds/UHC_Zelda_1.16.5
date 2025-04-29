package org.thony3ds.uHC_Zelda;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public final class CraftItems {

    public MaterialData getRubisMaterialData(int amount) {
        ItemStack rubis = new ItemStack(Material.GLOWSTONE_DUST, amount);
        ItemMeta rubis_meta = rubis.getItemMeta();
        rubis_meta.setDisplayName(ChatColor.GREEN + "Rubis");
        rubis.setItemMeta(rubis_meta);
        return rubis.getData();
    }
    public ItemStack getRubis(int amount) {
        ItemStack rubis = new ItemStack(Material.GLOWSTONE_DUST, amount);
        ItemMeta rubis_meta = rubis.getItemMeta();
        rubis_meta.setDisplayName(ChatColor.GREEN + "Rubis");
        rubis.setItemMeta(rubis_meta);
        return rubis;
    }

    public ItemStack getBoussoleTriforce(){
        ItemStack item = new ItemStack(Material.COMPASS, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Boussole de Triforce1"); // Voir item click dans merchant
        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getTriforceCourage(){
        ItemStack item = new ItemStack(Material.NETHERITE_SCRAP, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Triforce du Courage");
        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getTriforceForce(){
        ItemStack item = new ItemStack(Material.NETHERITE_SCRAP, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Triforce de la Force");
        item.setItemMeta(meta);

        return item;
    }
    public ItemStack getTriforceSagesse(){
        ItemStack item = new ItemStack(Material.NETHERITE_SCRAP, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Triforce de la Sagesse");
        item.setItemMeta(meta);

        return item;
    }

    public void Init_Crafts(){

        //Boussole de Triforce
        NamespacedKey boussoleKey = new NamespacedKey(UHC_Zelda.instance, "triforce_compass");
        ShapedRecipe boussoleTriforce = new ShapedRecipe(boussoleKey, getBoussoleTriforce());
        boussoleTriforce.shape("ABA", "CDE", "AFA");
        boussoleTriforce.setIngredient('A', Material.IRON_INGOT);
        boussoleTriforce.setIngredient('B', Material.COAL);
        boussoleTriforce.setIngredient('C', Material.GOLD_INGOT);
        boussoleTriforce.setIngredient('D', Material.REDSTONE);
        boussoleTriforce.setIngredient('E', Material.DIAMOND);
        boussoleTriforce.setIngredient('F', getRubisMaterialData(1));

        Bukkit.addRecipe(boussoleTriforce);
    }
}
