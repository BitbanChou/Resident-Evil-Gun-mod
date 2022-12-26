package com.joy187.re8gun.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.joy187.re8gun.init.RecipeInit;
import com.joy187.re8gun.init.RecipeSerializersInit;
import com.mrcrayfish.guns.crafting.WorkbenchIngredient;
import com.mrcrayfish.guns.init.ModRecipeSerializers;
import com.mrcrayfish.guns.util.InventoryUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.Iterator;

public class RE8WorkbenchRecipe implements Recipe<RE8WorkbenchBlockEntity> {
    public RE8WorkbenchRecipe() {
    }

    private ResourceLocation id;
    private ItemStack item;
    private ImmutableList<RE8WorkbenchIngredient> materials;

    public RE8WorkbenchRecipe(ResourceLocation id, ItemStack item, ImmutableList<RE8WorkbenchIngredient> materials) {
        this.id = id;
        this.item = item;
        this.materials = materials;
    }

    public ItemStack getItem() {
        return this.item.copy();
    }

    public ImmutableList<RE8WorkbenchIngredient> getMaterials() {
        return this.materials;
    }

    @Override
    public boolean matches(RE8WorkbenchBlockEntity p_44002_, Level p_44003_) {
        return false;
    }

    @Override
    public ItemStack assemble(RE8WorkbenchBlockEntity p_44001_) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int p_43999_, int p_44000_) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.item.copy();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return RecipeSerializersInit.RE8WORKBENCH.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeInit.RE8WORKBENCH.get();
    }

    public boolean hasMaterials(Player player) {
        UnmodifiableIterator var2 = this.getMaterials().iterator();

        RE8WorkbenchIngredient ingredient;
        do {
            if (!var2.hasNext()) {
                return true;
            }

            ingredient = (RE8WorkbenchIngredient)var2.next();
        } while(hasWorkstationIngredient(player, ingredient));

        return false;
    }

    public static boolean hasWorkstationIngredient(Player player, RE8WorkbenchIngredient find) {
        int count = 0;
        Iterator var3 = player.getInventory().items.iterator();

        while(var3.hasNext()) {
            ItemStack stack = (ItemStack)var3.next();
            if (!stack.isEmpty() && find.test(stack)) {
                count += stack.getCount();
            }
        }

        return find.getCount() <= count;
    }

    public void consumeMaterials(Player player) {
        UnmodifiableIterator var2 = this.getMaterials().iterator();

        while(var2.hasNext()) {
            RE8WorkbenchIngredient ingredient = (RE8WorkbenchIngredient)var2.next();
            removeWorkstationIngredient(player, ingredient);
        }

    }

    public static boolean removeWorkstationIngredient(Player player, RE8WorkbenchIngredient find) {
        int amount = find.getCount();

        for(int i = 0; i < player.getInventory().getContainerSize(); ++i) {
            ItemStack stack = player.getInventory().getItem(i);
            if (!stack.isEmpty() && find.test(stack)) {
                if (amount - stack.getCount() < 0) {
                    stack.shrink(amount);
                    return true;
                }

                amount -= stack.getCount();
                player.getInventory().items.set(i, ItemStack.EMPTY);
                if (amount == 0) {
                    return true;
                }
            }
        }

        return false;
    }

}
