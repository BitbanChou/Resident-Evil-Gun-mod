package com.joy187.re8gun;

import com.joy187.re8gun.block.RE8WorkbenchScreen;
import com.joy187.re8gun.config.ModConfigs;
import com.joy187.re8gun.datagen.RE8RecipeGen;
import com.joy187.re8gun.init.*;

import com.joy187.re8gun.network.RE8PacketHandler;
import com.mrcrayfish.guns.Config;
import com.mrcrayfish.guns.network.PacketHandler;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "re8gun";

    public static final Logger LOGGER = LogManager.getLogger(Main.MOD_ID);

    public static final CreativeModeTab TUTORIAL_TAB = new CreativeModeTab(MOD_ID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(BlockInit.RE8WORKBENCH.get());
        }
    };


    public Main()
    {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        ItemInit.ITEMS.register(bus);
        BlockInit.BLOCKS.register(bus);
        SoundInit.SOUNDS.register(bus);
        EntityInit.ENTITY_TYPES.register(bus);
        BlockEntityInit.BLOCK_ENTITIES.register(bus);
        ContainerInit.MENUS.register(bus);
        RecipeInit.RECIPES.register(bus);
        RecipeSerializersInit.SERIALIZERS.register(bus);
        //BlockInit.BLOCKS.register(bus);
//        ParticleInit.PARTICLE.register(bus);
//        EnchantmentInit.ENCHANT.register(bus);
        bus.addListener(this::commonSetup);
        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);
        bus.addListener(this::onGatherData);
        bus.addListener(RE8PacketHandler::onFrameworkRegister);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ModConfigs.clientConfig, "re8joymod-client.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ModConfigs.commonSpec,"re8joymod-common.toml");
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void clientSetup(final FMLClientSetupEvent event) {

        //ClientHandler.registerModelOverrides();
        event.enqueueWork(ClientHandler::setup);

    }


    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
//        LOGGER.info("HELLO FROM COMMON SETUP");
//        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
    }


    private void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeServer(), new RE8RecipeGen(generator));

    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ContainerInit.RE8WORKBENCH_MENU.get(), RE8WorkbenchScreen::new);
        }
    }
}
