package com.joy187.re8gun.block;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;


public class RE8WorkbenchRecipeSerializer implements RecipeSerializer<RE8WorkbenchRecipe> {
    public RE8WorkbenchRecipeSerializer() {
    }

    public RE8WorkbenchRecipe fromJson(ResourceLocation recipeId, JsonObject parent) {
        ImmutableList.Builder<RE8WorkbenchIngredient> builder = ImmutableList.builder();
        JsonArray input = GsonHelper.getAsJsonArray(parent, "materials");

        for(int i = 0; i < input.size(); ++i) {
            JsonObject object = input.get(i).getAsJsonObject();
            builder.add(RE8WorkbenchIngredient.fromJson(object));
        }

        if (!parent.has("result")) {
            throw new JsonSyntaxException("Missing result item entry");
        } else {
            JsonObject resultObject = GsonHelper.getAsJsonObject(parent, "result");
            ItemStack resultItem = ShapedRecipe.itemStackFromJson(resultObject);
            return new RE8WorkbenchRecipe(recipeId, resultItem, builder.build());
        }
    }

    @Nullable
    public RE8WorkbenchRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        ItemStack result = buffer.readItem();
        ImmutableList.Builder<RE8WorkbenchIngredient> builder = ImmutableList.builder();
        int size = buffer.readVarInt();

        for(int i = 0; i < size; ++i) {
            builder.add((RE8WorkbenchIngredient) Ingredient.fromNetwork(buffer));
        }

        return new RE8WorkbenchRecipe(recipeId, result, builder.build());
    }

//    @Override
//    public void toNetwork(FriendlyByteBuf p_44101_, RE8WorkbenchRecipe p_44102_) {
//
//    }
    @Override
    public void toNetwork(FriendlyByteBuf buffer, RE8WorkbenchRecipe recipe) {
        buffer.writeItem(recipe.getItem());
        buffer.writeVarInt(recipe.getMaterials().size());
        UnmodifiableIterator var3 = recipe.getMaterials().iterator();

        while(var3.hasNext()) {
            RE8WorkbenchIngredient ingredient = (RE8WorkbenchIngredient)var3.next();
            ingredient.toNetwork(buffer);
        }

    }
}
