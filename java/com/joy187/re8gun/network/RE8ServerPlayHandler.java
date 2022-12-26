package com.joy187.re8gun.network;

import com.joy187.re8gun.block.RE8WorkbenchBlockEntity;
import com.joy187.re8gun.block.RE8WorkbenchContainer;
import com.joy187.re8gun.block.RE8WorkbenchRecipe;
import com.joy187.re8gun.block.RE8WorkbenchRecipes;
import com.mrcrayfish.guns.item.IColored;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Containers;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RE8ServerPlayHandler {
    public RE8ServerPlayHandler() {
    }

    public static void handleCraft(ServerPlayer player, ResourceLocation id, BlockPos pos) {
        Level world = player.level;
        AbstractContainerMenu var5 = player.containerMenu;
        if (var5 instanceof RE8WorkbenchContainer) {
            RE8WorkbenchContainer workbench = (RE8WorkbenchContainer)var5;
            if (workbench.getPos().equals(pos)) {
                RE8WorkbenchRecipe recipe = RE8WorkbenchRecipes.getRecipeById(world, id);
                if (recipe == null || !recipe.hasMaterials(player)) {
                    return;
                }

                recipe.consumeMaterials(player);
                RE8WorkbenchBlockEntity workbenchBlockEntity = workbench.getWorkbench();
                ItemStack stack = recipe.getItem();
                ItemStack dyeStack = workbenchBlockEntity.getInventory().get(0);
                if (dyeStack.getItem() instanceof DyeItem) {
                    DyeItem dyeItem = (DyeItem)dyeStack.getItem();
                    int color = dyeItem.getDyeColor().getTextColor();
                    if (IColored.isDyeable(stack)) {
                        IColored colored = (IColored)stack.getItem();
                        colored.setColor(stack, color);
                        workbenchBlockEntity.getInventory().set(0, ItemStack.EMPTY);
                    }
                }

                Containers.dropItemStack(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.125D, (double)pos.getZ() + 0.5D, stack);
            }
        }

    }

}
