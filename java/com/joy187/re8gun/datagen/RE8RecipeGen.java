package com.joy187.re8gun.datagen;

import com.google.gson.JsonObject;
import com.joy187.re8gun.Main;
import com.joy187.re8gun.block.RE8WorkbenchIngredient;
import com.joy187.re8gun.block.RE8WorkbenchRecipeBuilder;
import com.joy187.re8gun.init.BlockInit;
import com.joy187.re8gun.init.ItemInit;
import com.mrcrayfish.guns.crafting.WorkbenchIngredient;
import com.mrcrayfish.guns.init.ModBlocks;
import com.mrcrayfish.guns.init.ModItems;
import com.mrcrayfish.guns.init.ModRecipeSerializers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RE8RecipeGen extends RecipeProvider {
    public RE8RecipeGen(DataGenerator generator) {
        super(generator);
    }

    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new FinishedRecipe() {
            public void serializeRecipeData(JsonObject json) {
            }

            public RecipeSerializer<?> getType() {
                return ModRecipeSerializers.DYE_ITEM.get();
            }

            public ResourceLocation getId() {
                return new ResourceLocation(Main.MOD_ID, "dye_item");
            }

            @Nullable
            public JsonObject serializeAdvancement() {
                return null;
            }

            public ResourceLocation getAdvancementId() {
                return null;
            }
        });

        ShapedRecipeBuilder.shaped(BlockInit.RE8WORKBENCH.get())
                .pattern("CRC")
                .pattern("III")
                .pattern("I I")
                .define('R', Blocks.RED_CONCRETE)
                .define('C', Blocks.BLACK_CONCRETE)
                .define('I', Tags.Items.INGOTS_IRON)
                .unlockedBy("has_red_concrete", has(Blocks.RED_CONCRETE))
                .unlockedBy("has_black_concrete", has(Blocks.BLACK_CONCRETE))
                .unlockedBy("has_iron", has(Tags.Items.INGOTS_IRON))
                .save(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.LEMI.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 10))
                .addIngredient(RE8WorkbenchIngredient.of(Items.STRING, 4))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_string", has(Items.STRING)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.M1911.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 16))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.LEATHER, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_lether", has(Tags.Items.LEATHER)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.V61.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 12))
                .addIngredient(RE8WorkbenchIngredient.of(Items.OAK_PLANKS, 12))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_oak_planks", has(Items.OAK_PLANKS)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.SAMURAI.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 16))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_NETHERITE, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_netherite_ingot", has(Tags.Items.INGOTS_NETHERITE)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.USMAI.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 16))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.END_STONES, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_end_stone", has(Tags.Items.END_STONES)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.GM79.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 24))
                .addIngredient(RE8WorkbenchIngredient.of(Items.OAK_PLANKS, 24))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_oak_planks", has(Items.OAK_PLANKS))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.ROCKETPISTOL.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 12))
                .addIngredient(RE8WorkbenchIngredient.of(Items.OAK_PLANKS, 16))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_oak_planks", has(Items.OAK_PLANKS))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.M1897.get()).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 32)).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_BLACK, 6)).
                addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).
                addCriterion("has_black_color", has(Tags.Items.DYES_BLACK)).
                build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.W870.get()).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 28)).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_BROWN, 4)).
                addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).
                addCriterion("has_brown_color", has(Tags.Items.DYES_BROWN)).
                build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.SYG12.get()).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 32)).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_GREEN ,6)).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_GRAY ,6)).
                addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).
                addCriterion("has_green_color", has(Tags.Items.DYES_GREEN)).
                addCriterion("has_gray_color", has(Tags.Items.DYES_GRAY)).
                build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.M1851.get()).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 40)).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_YELLOW, 8)).
                addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).
                addCriterion("has_yellow_color", has(Tags.Items.DYES_YELLOW)).
                build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.STAKE.get()).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 40)).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_WHITE, 8)).
                addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).
                addCriterion("has_white_color", has(Tags.Items.DYES_WHITE)).
                build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.HANDCANNON.get()).
                addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 48)).
                addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).
                build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.WCX.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 32))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.DRAGOON.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 28))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.F2.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 48))
                .addIngredient(RE8WorkbenchIngredient.of(Items.DIAMOND, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_diamond", has(Items.DIAMOND))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.SA110.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 40))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_GOLD, 8))
                .addIngredient(RE8WorkbenchIngredient.of(Items.DIAMOND, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON))
                .addCriterion("has_gold_ingot", has(Tags.Items.INGOTS_GOLD))
                .addCriterion("has_diamond", has(Items.DIAMOND))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.RIFLEAMMO.get(), 32)
                .addIngredient(RE8WorkbenchIngredient.of(Items.COPPER_INGOT, 8))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2))
                .addCriterion("has_copper_ingot", has(Items.COPPER_INGOT))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.HANDAMMO.get(), 64)
                .addIngredient(RE8WorkbenchIngredient.of(Items.COPPER_INGOT, 6))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2))
                .addCriterion("has_copper_ingot", has(Items.COPPER_INGOT))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.SNIPERAMMO.get(), 10)
                .addIngredient(RE8WorkbenchIngredient.of(Items.COPPER_INGOT, 8))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2))
                .addCriterion("has_copper_ingot", has(Items.COPPER_INGOT))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.M1851B.get(), 8)
                .addIngredient(RE8WorkbenchIngredient.of(Items.COPPER_INGOT, 2))
                .addIngredient(RE8WorkbenchIngredient.of(Items.GOLD_INGOT, 4))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2))
                .addCriterion("has_copper_ingot", has(Items.COPPER_INGOT))
                .addCriterion("has_gold_ingot", has(Items.GOLD_INGOT))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.M1879B.get(), 16)
                .addIngredient(RE8WorkbenchIngredient.of(Items.COPPER_INGOT, 3))
                .addIngredient(RE8WorkbenchIngredient.of(Items.IRON_INGOT, 3))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2))
                .addCriterion("has_copper_ingot", has(Items.COPPER_INGOT))
                .addCriterion("has_iron_ingot", has(Items.IRON_INGOT))
                .addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.KOBRA_SCOPE.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_COPPER, 4))
                .addIngredient(RE8WorkbenchIngredient.of(Items.AMETHYST_SHARD, 1))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DUSTS_REDSTONE, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .addCriterion("has_redstone", has(Tags.Items.DUSTS_REDSTONE)).build(consumer);


        RE8WorkbenchRecipeBuilder.crafting(ItemInit.LONGRANGE_8x_SCOPE.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.INGOTS_COPPER, 6))
                .addIngredient(RE8WorkbenchIngredient.of(Items.AMETHYST_SHARD, 2))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_BLACK, 2))
                .addCriterion("has_copper_ingot", has(Tags.Items.INGOTS_COPPER))
                .addCriterion("has_amethyst_shard", has(Items.AMETHYST_SHARD))
                .addCriterion("has_black_dye", has(Tags.Items.DYES_BLACK)).build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.RECOIL.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 6))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_BLACK, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_black_color", has(Tags.Items.DYES_BLACK))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.LONGB.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 8))
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.DYES_BLACK, 2))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_black_color", has(Tags.Items.DYES_BLACK))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.IMPROVE_STOCK.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 12))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.FOREGRIP.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 6))
                .addIngredient(RE8WorkbenchIngredient.of(Items.BLACK_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_black_wool", has(Items.BLACK_WOOL))
                .build(consumer);

        RE8WorkbenchRecipeBuilder.crafting(ItemInit.DRUMMAGAZINE.get())
                .addIngredient(RE8WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 10))
                .addIngredient(RE8WorkbenchIngredient.of(Items.BLACK_WOOL, 1))
                .addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON))
                .addCriterion("has_black_wool", has(Items.BLACK_WOOL))
                .build(consumer);

//        WorkbenchRecipeBuilder.crafting(ModItems.RIFLE.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 24)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.GRENADE_LAUNCHER.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 32)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.BAZOOKA.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 44)).addIngredient(net.minecraft.world.item.Items.REDSTONE, 4).addIngredient(WorkbenchIngredient.of(Tags.Items.DYES_RED, 1)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).addCriterion("has_redstone", has(net.minecraft.world.item.Items.REDSTONE)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.MINI_GUN.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 38)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.ASSAULT_RIFLE.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 28)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.MACHINE_PISTOL.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 20)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.HEAVY_RIFLE.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.INGOTS_IRON, 36)).addCriterion("has_iron_ingot", has(Tags.Items.INGOTS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.BASIC_BULLET.get(), 64).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.COPPER_INGOT, 4)).addIngredient(WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 1)).addCriterion("has_copper_ingot", has(net.minecraft.world.item.Items.COPPER_INGOT)).addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);

//        WorkbenchRecipeBuilder.crafting(ModItems.SHELL.get(), 48).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.COPPER_INGOT, 4)).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_GOLD, 1)).addIngredient(WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 1)).addCriterion("has_copper_ingot", has(net.minecraft.world.item.Items.COPPER_INGOT)).addCriterion("has_gold_nugget", has(Tags.Items.NUGGETS_GOLD)).addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.MISSILE.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 2)).addIngredient(WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 4)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.GRENADE.get(), 2).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 1)).addIngredient(WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 4)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.STUN_GRENADE.get(), 2).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 1)).addIngredient(WorkbenchIngredient.of(Tags.Items.GUNPOWDER, 2)).addIngredient(WorkbenchIngredient.of(Tags.Items.DUSTS_GLOWSTONE, 4)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gunpowder", has(Tags.Items.GUNPOWDER)).addCriterion("has_glowstone", has(Tags.Items.DUSTS_GLOWSTONE)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.SHORT_SCOPE.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 2)).addIngredient(WorkbenchIngredient.of(Tags.Items.GLASS_PANES, 1)).addIngredient(WorkbenchIngredient.of(Tags.Items.DUSTS_REDSTONE, 2)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_glass_pane", has(Tags.Items.GLASS_PANES)).addCriterion("has_redstone", has(Tags.Items.DUSTS_REDSTONE)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.MEDIUM_SCOPE.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 4)).addIngredient(WorkbenchIngredient.of(Tags.Items.GLASS_PANES, 1)).addIngredient(WorkbenchIngredient.of(Tags.Items.DUSTS_REDSTONE, 4)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_glass_pane", has(Tags.Items.GLASS_PANES)).addCriterion("has_redstone", has(Tags.Items.DUSTS_REDSTONE)).build(consumer);

//        WorkbenchRecipeBuilder.crafting(ModItems.SILENCER.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 4)).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.SPONGE, 1)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.LIGHT_STOCK.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 6)).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.GRAY_WOOL, 1)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gray_wool", has(net.minecraft.world.item.Items.GRAY_WOOL)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.TACTICAL_STOCK.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 8)).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.GRAY_WOOL, 1)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gray_wool", has(net.minecraft.world.item.Items.GRAY_WOOL)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.WEIGHTED_STOCK.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 12)).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.GRAY_WOOL, 1)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gray_wool", has(net.minecraft.world.item.Items.GRAY_WOOL)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.LIGHT_GRIP.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 4)).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.GRAY_WOOL, 1)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gray_wool", has(net.minecraft.world.item.Items.GRAY_WOOL)).build(consumer);
//        WorkbenchRecipeBuilder.crafting(ModItems.SPECIALISED_GRIP.get()).addIngredient(WorkbenchIngredient.of(Tags.Items.NUGGETS_IRON, 8)).addIngredient(WorkbenchIngredient.of(net.minecraft.world.item.Items.GRAY_WOOL, 1)).addCriterion("has_iron_ingot", has(Tags.Items.NUGGETS_IRON)).addCriterion("has_gray_wool", has(net.minecraft.world.item.Items.GRAY_WOOL)).build(consumer);

    }
}
