package com.joy187.re8gun;


import com.joy187.re8gun.block.RE8WorkbenchScreen;
import com.joy187.re8gun.init.BlockInit;
import com.joy187.re8gun.init.ContainerInit;
import com.joy187.re8gun.init.ItemInit;
import com.joy187.re8gun.item.model.KobraScopeModel;
import com.joy187.re8gun.item.model.LongRange8xScopeModel;
import com.mrcrayfish.guns.client.render.gun.ModelOverrides;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.MouseSettingsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(
        modid = Main.MOD_ID,
        value = {Dist.CLIENT}
)
public class ClientHandler {

    private static Field mouseOptionsField;

    public static void setup() {
        setupRenderLayers();
        registerScreenFactories();
        registerModelOverrides();
    }

    private static void setupRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(BlockInit.RE8WORKBENCH.get(), RenderType.cutout());
    }

    private static void registerScreenFactories()
    {
        //MenuScreens.register(ContainerInit.RE8WORKBENCH_MENU.get(), RE8WorkbenchScreen::new);
    }

    public static void registerModelOverrides() {
        ModelOverrides.register(ItemInit.KOBRA_SCOPE.get(), new KobraScopeModel());
        ModelOverrides.register(ItemInit.LONGRANGE_8x_SCOPE.get(), new LongRange8xScopeModel());
        //ModelOverrides.register(ItemRegistry.ULTRA_LONG_SCOPE.get(), new LongScopeModel());
    }

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        Screen var2 = event.getScreen();
        if (var2 instanceof MouseSettingsScreen) {
            MouseSettingsScreen screen = (MouseSettingsScreen)var2;
            if (mouseOptionsField == null) {
                mouseOptionsField = ObfuscationReflectionHelper.findField(MouseSettingsScreen.class, "f_96218_");
                mouseOptionsField.setAccessible(true);
            }

            try {
                OptionsList var4 = (OptionsList)mouseOptionsField.get(screen);
            } catch (IllegalAccessException var3) {
                var3.printStackTrace();
            }
        }

    }
//    @Mod.EventBusSubscriber(modid = Main.MOD_ID, value = Dist.CLIENT)
//    public static class ClientForgeEvents {
//        @SubscribeEvent
//        public static void onKeyInput(InputEvent.Key event) {
//            if(KeyBinding.DRINKING_KEY.consumeClick()) {
//                ModMessages.sendToServer(new DrinkWaterC2SPacket());
//            }
//        }
//    }

}
