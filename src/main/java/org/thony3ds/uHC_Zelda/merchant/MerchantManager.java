package org.thony3ds.uHC_Zelda.merchant;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.VillagerCareerChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.thony3ds.uHC_Zelda.basicItem.CraftItems;

import java.util.ArrayList;
import java.util.List;

public final class MerchantManager implements Listener {
    CraftItems craftItems = new CraftItems();

    public void spawnCustomMerchant(Location loc, String name){
        World world = loc.getWorld();
        Villager villager = (Villager) world.spawnEntity(loc, EntityType.VILLAGER);

        villager.setProfession(Villager.Profession.LIBRARIAN);
        villager.setCustomName(name);
        villager.setCustomNameVisible(true);
        villager.setInvulnerable(true);
        villager.setAI(false);

        List<MerchantRecipe> recipes = new ArrayList<>();

        switch (name){
            case "Zelda":
                ItemStack itemVendu = new ItemStack(Material.NETHERITE_INGOT);
                ItemMeta meta = itemVendu.getItemMeta();
                meta.setDisplayName("Ocarina du Temps"); //TODO change
                itemVendu.setItemMeta(meta);

                ItemStack prix = craftItems.getRubis(10);

                MerchantRecipe recette = new MerchantRecipe(itemVendu, 3);
                recette.addIngredient(prix);
                recipes.add(recette);

                break;
            case "Lavio":
                ItemStack itemVendu2 = new ItemStack(Material.NETHERITE_INGOT);
                ItemMeta meta2 = itemVendu2.getItemMeta();
                meta2.setDisplayName("Ocarina du Temps"); //TODO change
                itemVendu2.setItemMeta(meta2);

                ItemStack prix2 = craftItems.getRubis(10);

                MerchantRecipe recette2 = new MerchantRecipe(itemVendu2, 3);
                recette2.addIngredient(prix2);
                recipes.add(recette2);

                break;
            case "Saria":
                ItemStack itemVendu3 = new ItemStack(Material.NETHERITE_INGOT);
                ItemMeta meta3 = itemVendu3.getItemMeta();
                meta3.setDisplayName("Ocarina du Temps"); //TODO change
                itemVendu3.setItemMeta(meta3);

                ItemStack prix3 = craftItems.getRubis(10);

                MerchantRecipe recette3 = new MerchantRecipe(itemVendu3, 3);
                recette3.addIngredient(prix3);
                recipes.add(recette3);
                break;
            case "Dragon d'Eau":
                ItemStack itemVendu4 = new ItemStack(Material.NETHERITE_INGOT);
                ItemMeta meta4 = itemVendu4.getItemMeta();
                meta4.setDisplayName("Ocarina du Temps"); //TODO change
                itemVendu4.setItemMeta(meta4);

                ItemStack prix4 = craftItems.getRubis(10);

                MerchantRecipe recette4 = new MerchantRecipe(itemVendu4, 3);
                recette4.addIngredient(prix4);
                recipes.add(recette4);

                break;
            default:
                break;
        }

        villager.setRecipes(recipes);
    }

    @EventHandler
    public void onVillagerChangeProfession(VillagerCareerChangeEvent event){
        event.setCancelled(true);
    }
}
