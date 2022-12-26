package com.joy187.re8gun.block;

import com.joy187.re8gun.init.RecipeInit;
import com.mrcrayfish.guns.init.ModRecipeTypes;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.stream.Collectors;

public class RE8WorkbenchRecipes {
    public RE8WorkbenchRecipes() {
    }

    public static boolean isEmpty(Level world) {
        return world.getRecipeManager().getRecipes().stream().noneMatch((recipe) -> {
            return recipe.getType() == RecipeInit.RE8WORKBENCH.get();
        });
    }

    public static NonNullList<RE8WorkbenchRecipe> getAll(Level world) {
        return world.getRecipeManager().getRecipes().stream().filter((recipe) -> {
            return recipe.getType() == RecipeInit.RE8WORKBENCH.get();
        }).map((recipe) -> {
            return (RE8WorkbenchRecipe)recipe;
        }).collect(Collectors.toCollection(NonNullList::create));
    }

    @Nullable
    public static RE8WorkbenchRecipe getRecipeById(Level world, ResourceLocation id) {
        return world.getRecipeManager().getRecipes().stream().filter((recipe) -> {
            return recipe.getType() == RecipeInit.RE8WORKBENCH.get();
        }).map((recipe) -> {
            return (RE8WorkbenchRecipe)recipe;
        }).filter((recipe) -> {
            return recipe.getId().equals(id);
        }).findFirst().orElse(null);
    }

//    @Nullable
//    public static RE8WorkbenchRecipe getRecipeById(Level world, ResourceLocation id)
//    {
//        return world.getRecipeManager().getRecipes().stream()
//                .filter(recipe -> recipe.getType() == RecipeType.WORKBENCH)
//                .map(recipe -> (WorkbenchRecipe) recipe)
//                .filter(recipe -> recipe.getId().equals(id))
//                .findFirst().orElse(null);
//    }
}
