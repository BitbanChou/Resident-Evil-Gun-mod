package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.block.RE8WorkbenchRecipeSerializer;
import com.mrcrayfish.guns.crafting.DyeItemRecipe;
import com.mrcrayfish.guns.crafting.WorkbenchRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeSerializersInit {
//    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
//            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MOD_ID);
//
//    public static final RegistryObject<RE8WorkbenchRecipeSerializer> RE8WORKBENCH_SERIALIZER =
//            SERIALIZERS.register("re8workbench", RE8WorkbenchRecipeSerializer::new);
//
//    public static void register(IEventBus eventBus) {
//        SERIALIZERS.register(eventBus);
//    }

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS;
    //public static final RegistryObject<SimpleRecipeSerializer<DyeItemRecipe>> DYE_ITEM;
    public static final RegistryObject<RE8WorkbenchRecipeSerializer> RE8WORKBENCH;

    public RecipeSerializersInit() {
    }

    static {
        SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Main.MOD_ID);
//        DYE_ITEM = SERIALIZERS.register("dye_item", () -> {
//            return new SimpleRecipeSerializer(DyeItemRecipe::new);
//        });
        RE8WORKBENCH = SERIALIZERS.register("re8workbench", RE8WorkbenchRecipeSerializer::new);
    }

}
