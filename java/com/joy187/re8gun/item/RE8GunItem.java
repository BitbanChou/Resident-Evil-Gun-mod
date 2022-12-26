package com.joy187.re8gun.item;

import com.joy187.re8gun.RE8NetworkGunManager;
import com.joy187.re8gun.config.ModConfigs;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.interfaces.IGunModifier;
import com.mrcrayfish.guns.item.GunItem;
import net.minecraft.world.item.ItemStack;

/**
 * Author: Autovw
 */
public class RE8GunItem extends GunItem {

    private final boolean canColor;
    private Gun gun = new Gun();
    /**
     * @param properties The item properties
     * @param canColor If the gun can be colored or not
     */
    public RE8GunItem(Properties properties, boolean canColor) {
        super(properties);
        this.canColor = canColor;
    }

    @Override
    public boolean canColor(ItemStack stack) {
        return this.canColor;
    }

    /**
     * Makes it possible to disable the enchantment glint on guns client-side.
     */
    @Override
    public boolean isFoil(ItemStack stack) {
        if (stack.isEnchanted()) {
            return ModConfigs.Client.enableGunEnchantmentGlint.get();
        }
        else {
            return false;
        }
    }

    public static final IGunModifier LONGRANGE_8x_SCOPE_ADS = new IGunModifier()
    {
        @Override
        public double modifyAimDownSightSpeed(double speed)
        {
            return speed * 0.75F;
        }

    };

    public void setGun(RE8NetworkGunManager.Supplier supplier) {
        this.gun = supplier.getGun();
    }

    public Gun getGun() {
        return this.gun;
    }
}

