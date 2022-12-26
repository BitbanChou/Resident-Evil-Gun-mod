package com.joy187.re8gun.block;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.joy187.re8gun.init.RecipeSerializersInit;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class RE8WorkbenchRecipeBuilder {
    private final Item result;
    private final int count;
    private final List<RE8WorkbenchIngredient> ingredients;
    private final Advancement.Builder advancementBuilder;
    private final List<ICondition> conditions = new ArrayList<>();

    private RE8WorkbenchRecipeBuilder(ItemLike item, int count) {
        this.result = item.asItem();
        this.count = count;
        this.ingredients = new ArrayList<>();
        this.advancementBuilder = Advancement.Builder.advancement();
    }

    public static RE8WorkbenchRecipeBuilder crafting(ItemLike item) {
        return new RE8WorkbenchRecipeBuilder(item, 1);
    }

    public static RE8WorkbenchRecipeBuilder crafting(ItemLike item, int count) {
        return new RE8WorkbenchRecipeBuilder(item, count);
    }

    public RE8WorkbenchRecipeBuilder addIngredient(ItemLike item, int count) {
        this.ingredients.add(RE8WorkbenchIngredient.of(item, count));
        return this;
    }

    public RE8WorkbenchRecipeBuilder addIngredient(RE8WorkbenchIngredient ingredient) {
        this.ingredients.add(ingredient);
        return this;
    }

    public RE8WorkbenchRecipeBuilder addCriterion(String name, CriterionTriggerInstance criterionIn) {
        this.advancementBuilder.addCriterion(name, criterionIn);
        return this;
    }

    public RE8WorkbenchRecipeBuilder addCondition(ICondition condition) {
        this.conditions.add(condition);
        return this;
    }

    public void build(Consumer<FinishedRecipe> consumer) {
        ResourceLocation resourcelocation = Registry.ITEM.getKey(this.result);
        this.build(consumer, resourcelocation);
    }

    public void build(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        this.advancementBuilder.parent(new ResourceLocation("recipes/root")).addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id)).rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id)).requirements(RequirementsStrategy.OR);
        Item var10004 = this.result;
        int var10005 = this.count;
        List var10006 = this.ingredients;
        List var10007 = this.conditions;
        Advancement.Builder var10008 = this.advancementBuilder;
        String var10011 = id.getNamespace();
        String var10012 = this.result.getItemCategory().getRecipeFolderName();
        consumer.accept(new RE8WorkbenchRecipeBuilder.Result(id, var10004, var10005, var10006, var10007, var10008, new ResourceLocation(var10011, "recipes/" + var10012 + "/" + id.getPath())));
    }

    private void validate(ResourceLocation id) {
        if (this.advancementBuilder.getCriteria().isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + id);
        }
    }

    public static class Result implements FinishedRecipe {
        private final ResourceLocation id;
        private final Item item;
        private final int count;
        private final List<RE8WorkbenchIngredient> ingredients;
        private final List<ICondition> conditions;
        private final Advancement.Builder advancement;
        private final ResourceLocation advancementId;

        public Result(ResourceLocation id, ItemLike item, int count, List<RE8WorkbenchIngredient> ingredients, List<ICondition> conditions, Advancement.Builder advancement, ResourceLocation advancementId) {
            this.id = id;
            this.item = item.asItem();
            this.count = count;
            this.ingredients = ingredients;
            this.conditions = conditions;
            this.advancement = advancement;
            this.advancementId = advancementId;
        }

        public void serializeRecipeData(JsonObject json) {
            JsonArray conditions = new JsonArray();
            this.conditions.forEach((condition) -> {
                conditions.add(CraftingHelper.serialize(condition));
            });
            if (conditions.size() > 0) {
                json.add("conditions", conditions);
            }

            JsonArray materials = new JsonArray();
            this.ingredients.forEach((ingredient) -> {
                materials.add(ingredient.toJson());
            });
            json.add("materials", materials);
            JsonObject resultObject = new JsonObject();
            resultObject.addProperty("item", Registry.ITEM.getKey(this.item).toString());
            if (this.count > 1) {
                resultObject.addProperty("count", this.count);
            }

            json.add("result", resultObject);
        }

        public ResourceLocation getId() {
            return this.id;
        }

        public RecipeSerializer<?> getType() {
            return RecipeSerializersInit.RE8WORKBENCH.get();
        }

        public JsonObject serializeAdvancement() {
            return this.advancement.serializeToJson();
        }

        public ResourceLocation getAdvancementId() {
            return this.advancementId;
        }
    }
}