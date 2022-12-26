package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.block.RE8WorkbenchRecipe;
import com.mrcrayfish.guns.crafting.WorkbenchRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RecipeInit {
//    public static final DeferredRegister<RecipeType<?>> SERIALIZERS =
//            DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Main.MOD_ID);
//
//    public static final RegistryObject<RecipeType<RE8WorkbenchRecipe>> RE8WORKBENCH_SERIALIZER =
//            create("re8workbench");
//
//    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> create(String name) {
//        return SERIALIZERS.register(name, () -> {
//            return new RecipeType<T>() {
//                public String toString() {
//                    return name;
//                }
//            };
//        });
//    }
//
//    public static void register(IEventBus eventBus) {
//        SERIALIZERS.register(eventBus);
//    }
//
    public static final DeferredRegister<RecipeType<?>> RECIPES;
    public static final RegistryObject<RecipeType<RE8WorkbenchRecipe>> RE8WORKBENCH;

    public RecipeInit() {
    }

    private static <T extends Recipe<?>> RegistryObject<RecipeType<T>> create(String name) {
        return RECIPES.register(name, () -> {
            return new RecipeType<T>() {
                public String toString() {
                    return name;
                }
            };
        });
    }

    static {
        RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Main.MOD_ID);
        RE8WORKBENCH = create("re8workbench");
    }
}
