package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import net.minecraftforge.registries.RegistryObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SoundInit {

    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MOD_ID);

    public static final RegistryObject<SoundEvent> GM79PRO = build("entity.gm79.gm79pro");
    public static final RegistryObject<SoundEvent> M1897 = build("entity.gm79.m1897");
    public static final RegistryObject<SoundEvent> W870 = build("entity.gm79.w870");
    public static final RegistryObject<SoundEvent> SYG12 = build("entity.gm79.syg12");

    public static final RegistryObject<SoundEvent> M1851 = build("entity.m1851.m1851");
    public static final RegistryObject<SoundEvent> PZ = build("entity.m1851.pz");
    public static final RegistryObject<SoundEvent> F2RIFLE = build("entity.m1851.f2rifle");
    public static final RegistryObject<SoundEvent> RIFLE = build("entity.m1851.rifle");
    public static final RegistryObject<SoundEvent> LEMI = build("entity.m1851.lemi");
    public static final RegistryObject<SoundEvent> V61 = build("entity.m1851.v61");
    public static final RegistryObject<SoundEvent> M1911 = build("entity.m1851.m1911");

    private static RegistryObject<SoundEvent> build(String id)
    {
        return SOUNDS.register(id, () -> new SoundEvent(new ResourceLocation(Main.MOD_ID, id)));
    }
}
