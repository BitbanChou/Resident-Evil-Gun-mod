package com.joy187.re8gun.block;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.IIngredientSerializer;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;

public class RE8WorkbenchIngredient extends Ingredient {
    private final Value itemList;
    private final int count;

    protected RE8WorkbenchIngredient(Stream<? extends Value> itemList, int count) {
        super(itemList);
        this.itemList = null;
        this.count = count;
    }

    private RE8WorkbenchIngredient(Value itemList, int count) {
        super(Stream.of(itemList));
        this.itemList = itemList;
        this.count = count;
    }

    public int getCount() {
        return this.count;
    }

    public IIngredientSerializer<? extends Ingredient> getSerializer() {
        return RE8WorkbenchIngredient.Serializer.INSTANCE;
    }

    public static RE8WorkbenchIngredient fromJson(JsonObject object) {
        Value value = valueFromJson(object);
        int count = GsonHelper.getAsInt(object, "count", 1);
        return new RE8WorkbenchIngredient(Stream.of(value), count);
    }

    public JsonElement toJson() {
        JsonObject object = this.itemList.serialize();
        object.addProperty("count", this.count);
        return object;
    }

    public static RE8WorkbenchIngredient of(ItemLike provider, int count) {
        return new RE8WorkbenchIngredient(new ItemValue(new ItemStack(provider)), count);
    }

    public static RE8WorkbenchIngredient of(ItemStack stack, int count) {
        return new RE8WorkbenchIngredient(new ItemValue(stack), count);
    }

    public static RE8WorkbenchIngredient of(TagKey<Item> tag, int count) {
        return new RE8WorkbenchIngredient(new TagValue(tag), count);
    }

    public static RE8WorkbenchIngredient of(ResourceLocation id, int count) {
        return new RE8WorkbenchIngredient(new RE8WorkbenchIngredient.UnknownValue(id), count);
    }

    public static class Serializer implements IIngredientSerializer<RE8WorkbenchIngredient> {
        public static final RE8WorkbenchIngredient.Serializer INSTANCE = new RE8WorkbenchIngredient.Serializer();

        public Serializer() {
        }

        public RE8WorkbenchIngredient parse(FriendlyByteBuf buffer) {
            int itemCount = buffer.readVarInt();
            int count = buffer.readVarInt();
            Stream<ItemValue> values = Stream.generate(() -> {
                return new ItemValue(buffer.readItem());
            }).limit(itemCount);
            return new RE8WorkbenchIngredient(values, count);
        }

        public RE8WorkbenchIngredient parse(JsonObject object) {
            return RE8WorkbenchIngredient.fromJson(object);
        }

        public void write(FriendlyByteBuf buffer, RE8WorkbenchIngredient ingredient) {
            buffer.writeVarInt(ingredient.getItems().length);
            buffer.writeVarInt(ingredient.count);
            ItemStack[] var3 = ingredient.getItems();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                ItemStack stack = var3[var5];
                buffer.writeItem(stack);
            }

        }
    }

    public static class UnknownValue implements Value {
        private final ResourceLocation id;

        public UnknownValue(ResourceLocation id) {
            this.id = id;
        }

        public Collection<ItemStack> getItems() {
            return Collections.emptyList();
        }

        public JsonObject serialize() {
            JsonObject object = new JsonObject();
            object.addProperty("item", this.id.toString());
            return object;
        }
    }
}

