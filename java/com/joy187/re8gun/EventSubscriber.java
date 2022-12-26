package com.joy187.re8gun;

import com.joy187.re8gun.init.ItemInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventSubscriber {

    @SubscribeEvent
    public static void onMissingMappingsEvent(final MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getAllMappings(ForgeRegistries.Keys.ITEMS)) {
            if (mapping.getKey().equals(new ResourceLocation(Main.MOD_ID, "rifleammo"))) {
                mapping.remap(ItemInit.RIFLEAMMO.get());
            }
            if (mapping.getKey().equals(new ResourceLocation(Main.MOD_ID, "m1851b"))) {
                mapping.remap(ItemInit.M1851B.get());
            }
            if (mapping.getKey().equals(new ResourceLocation(Main.MOD_ID, "m1879b"))) {
                mapping.remap(ItemInit.M1879B.get());
            }
            if (mapping.getKey().equals(new ResourceLocation(Main.MOD_ID, "sniperammo"))) {
                mapping.remap(ItemInit.SNIPERAMMO.get());
            }
            if (mapping.getKey().equals(new ResourceLocation(Main.MOD_ID, "handammo"))) {
                mapping.remap(ItemInit.HANDAMMO.get());
            }
//            if (mapping.getKey().equals(new ResourceLocation(Main.MOD_ID, "m1851b"))) {
//                mapping.remap(ItemInit.M1851B.get());
//            }
        }
    }

}
