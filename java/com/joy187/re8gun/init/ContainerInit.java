package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.block.RE8WorkbenchContainer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerInit {
//    public static final DeferredRegister<MenuType<?>> MENUS =
//            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);
//
//    public static final RegistryObject<MenuType<RE8WorkbenchContainer>> RE8WORKBENCH_MENU = register("re8workbench", (IContainerFactory<RE8WorkbenchContainer>) (windowId, playerInventory, data) -> {
//        RE8WorkbenchBlockEntity workstation = (RE8WorkbenchBlockEntity)playerInventory.player.level.getBlockEntity(data.readBlockPos());
//        return new RE8WorkbenchContainer(windowId, playerInventory, workstation);
//    });
//
//    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
//                                                                                                  String name) {
//        return MENUS.register(name, () -> IForgeMenuType.create(factory));
//    }
//
//    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory) {
//        return MENUS.register(id, () -> {
//            return new MenuType(factory);
//        });
//    }
//
//    public static void register(IEventBus eventBus) {
//        MENUS.register(eventBus);
//    }
    public static final DeferredRegister<MenuType<?>> MENUS =
        DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);
    public static final RegistryObject<MenuType<RE8WorkbenchContainer>> RE8WORKBENCH_MENU =
            registerMenuType(RE8WorkbenchContainer::new, "re8workbench");

//    public static final RegistryObject<MenuType<VirusGeneratorMenu>> RE8WORKBENCH_MENU =
//            registerMenuType(VirusGeneratorMenu::new, "virus_generator_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                  String name) {
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }

//    public static final DeferredRegister<MenuType<?>> MENUS;
//    public static final RegistryObject<MenuType<RE8WorkbenchContainer>> RE8WORKBENCH_MENU;
//
//    public ContainerInit() {
//    }
//
//    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> register(String id, MenuType.MenuSupplier<T> factory) {
//        return MENUS.register(id, () -> {
//            return new MenuType(factory);
//        });
//    }
//
//    static {
//        MENUS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Main.MOD_ID);
//        RE8WORKBENCH_MENU = register("re8workbench",(IContainerFactory<RE8WorkbenchContainer>)  (windowId, playerInventory, data) -> {
//            RE8WorkbenchBlockEntity workstation = (RE8WorkbenchBlockEntity)playerInventory.player.level.getBlockEntity(data.readBlockPos());
//            return new RE8WorkbenchContainer(windowId, playerInventory, workstation);
//        });
//    }
}
