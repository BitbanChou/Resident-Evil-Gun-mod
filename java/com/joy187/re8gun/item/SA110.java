package com.joy187.re8gun.item;

import com.joy187.re8gun.item.model.KobraScopeModel;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.common.ReloadTracker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;

import java.util.WeakHashMap;

public class SA110 extends RE8GunItem{

    private WeakHashMap<CompoundTag, Gun> modifiedGunCache = new WeakHashMap();

    public SA110(Properties properties, boolean canColor) {
        super(properties, canColor);
    }


}
