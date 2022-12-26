package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.block.RE8WorkbenchBlock;
import com.mrcrayfish.guns.block.WorkbenchBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StainedGlassBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {
    public static DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = ItemInit.ITEMS;

//    public static final RegistryObject<Block> EXAMPLE_BLOCK = register("example_block",
//            () -> new Block(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.COLOR_PURPLE).strength(5f, 6f)
//                    .sound(SoundType.METAL).requiresCorrectToolForDrops()),
//            object -> () -> new BlockItem(object.get(), new Item.Properties().tab(Main.TUTORIAL_TAB)));

    public static final RegistryObject<Block> RE8WORKBENCH = register("re8guntable",
            () -> new RE8WorkbenchBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1.5F)),
            object -> () -> new BlockItem(object.get(), new Item.Properties().tab(Main.TUTORIAL_TAB)));

//    public static final RegistryObject<Block> RE8WORKBENCH = registerBlock("re8guntable",
//            () -> new RE8WorkbenchBlock(BlockBehaviour.Properties.of(Material.METAL).strength(1.5F)),
//            Main.TUTORIAL_TAB);
//    public static final RegistryObject<Block> WORKBENCH = register("re8guntable", () -> new WorkbenchBlock(Block.Properties.of(Material.METAL).strength(1.5F))
//    {
//        @Override
//        public void onPlayerDestroy(Level worldIn, BlockPos pos, BlockState state)
//        {
//            Block.spawnAsEntity(worldIn,pos,WORKBENCH.get().getItem(worldIn,pos,state));
//        }
//    },true);

    private static <T extends Block> RegistryObject<T> registerBlock(final String name,
                                                                     final Supplier<? extends T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistryObject<T> register(final String name, final Supplier<? extends T> block,
                                                                Function<RegistryObject<T>, Supplier<? extends Item>> item) {
        RegistryObject<T> obj = registerBlock(name, block);
        ITEMS.register(name, item.apply(obj));
        return obj;
    }

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ItemInit.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    public static Supplier<Block> createStainedGlassFromColor(DyeColor color) {
        return () -> new StainedGlassBlock(color, BlockBehaviour.Properties.of(Material.GLASS, color).strength(0.3F)
                .sound(SoundType.GLASS).noOcclusion().isValidSpawn(BlockInit::never).isRedstoneConductor(BlockInit::never).isSuffocating(BlockInit::never).isViewBlocking(BlockInit::never));
    }

    public static boolean always(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }

    public static boolean never(BlockState state, BlockGetter reader, BlockPos pos) {
        return false;
    }

    public static boolean always(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entityType) {
        return true;
    }

    public static boolean never(BlockState state, BlockGetter reader, BlockPos pos, EntityType<?> entityType) {
        return false;
    }
}
