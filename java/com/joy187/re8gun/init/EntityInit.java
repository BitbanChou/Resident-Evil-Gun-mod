package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.entity.EntityGM79B;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityInit {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES,
            Main.MOD_ID);

    public static final RegistryObject<EntityType<EntityGM79B>> GM79B = ENTITY_TYPES.register("gm79b",
            () -> EntityType.Builder.<EntityGM79B>of(EntityGM79B::new, MobCategory.MISC).sized(0.25F, 0.25F)
                    .setTrackingRange(4).updateInterval(10).build("gm79b"));


}
