package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.block.RE8WorkbenchBlockEntity;
import com.mojang.datafixers.types.Type;
import com.mrcrayfish.guns.blockentity.WorkbenchBlockEntity;
import com.mrcrayfish.guns.init.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class BlockEntityInit {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);
//
    public static final RegistryObject<BlockEntityType<RE8WorkbenchBlockEntity>> RE8WORKBENCHENTITY = BLOCK_ENTITIES.register("re8workbench",
            () -> BlockEntityType.Builder.of(RE8WorkbenchBlockEntity::new, BlockInit.RE8WORKBENCH.get()).build(null));

//    public static void register(IEventBus eventBus) {
//        BLOCK_ENTITIES.register(eventBus);
//    }
    //public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES;
    //public static final RegistryObject<BlockEntityType<RE8WorkbenchBlockEntity>> RE8WORKBENCHENTITY;

    public BlockEntityInit() {
    }

//    public static final RegistryObject<BlockEntityType<RE8WorkbenchBlockEntity>> RE8WORKBENCHENTITY = register("re8workbench", RE8WorkbenchBlockEntity::new, () -> new Block[]{BlockInit.RE8WORKBENCH.get()});
//
//    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier)
//    {
//        return BLOCK_ENTITIES.register(id, () -> BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null));
//    }

//    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factoryIn, Supplier<Block[]> validBlocksSupplier) {
//        return BLOCK_ENTITIES.register(id, () -> {
//            return BlockEntityType.Builder.of(factoryIn, validBlocksSupplier.get()).build(null);
//        });
//    }
//
//    static {
//        BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MOD_ID);
//        RE8WORKBENCHENTITY = register("re8workbench", RE8WorkbenchBlockEntity::new, () -> {
//            return new Block[]{BlockInit.RE8WORKBENCH.get()};
//        });
//    }
}
