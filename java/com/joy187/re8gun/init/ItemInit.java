package com.joy187.re8gun.init;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.item.RE8GrenadeItem;
import com.joy187.re8gun.item.RE8GunItem;
import com.joy187.re8gun.util.RE8IGunModifier;
import com.mrcrayfish.guns.GunMod;
import com.mrcrayfish.guns.common.GunModifiers;
import com.mrcrayfish.guns.interfaces.IGunModifier;
import com.mrcrayfish.guns.item.*;
import com.mrcrayfish.guns.item.attachment.impl.Barrel;
import com.mrcrayfish.guns.item.attachment.impl.Scope;
import com.mrcrayfish.guns.item.attachment.impl.Stock;
import com.mrcrayfish.guns.item.attachment.impl.UnderBarrel;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MOD_ID);

    public static final Item.Properties genericProperties = new Item.Properties().stacksTo(1).tab(Main.TUTORIAL_TAB);

//    public static final RegistryObject<Item> LEI = register("lei",
//            () -> new Item(new Item.Properties().tab(Main.TUTORIAL_TAB)));
    //public static final RegistryObject<Item> VINTOREZ = registerGun("vintorez", false);
    //public static final RegistryObject<Item> GM79 = registerGun("gm79", true);

    public static final RegistryObject<Item> LEMI = registerGun("lemi", true);
    public static final RegistryObject<Item> M1911 = registerGun("m1911", true);
    public static final RegistryObject<Item> V61 = registerGun("v61", true);
    public static final RegistryObject<Item> SAMURAI = registerGun("samurai", true);
    public static final RegistryObject<Item> USMAI = registerGun("usmai", true);

    public static final RegistryObject<GunItem> GM79 = ITEMS.register("gm79", () -> {
        return new GunItem((new Item.Properties()).stacksTo(1).tab(Main.TUTORIAL_TAB));
    });
    public static final RegistryObject<Item> ROCKETPISTOL = registerGun("rocketpistol", true);

    public static final RegistryObject<Item> M1897 = registerGun("m1897", true);
    public static final RegistryObject<Item> W870 = registerGun("w870", true);
    public static final RegistryObject<Item> SYG12 = registerGun("syg12", true);

    public static final RegistryObject<Item> M1851 = registerGun("m1851", true);
    public static final RegistryObject<Item> STAKE = registerGun("stake", true);
    public static final RegistryObject<Item> HANDCANNON = registerGun("handcannon", true);

    public static final RegistryObject<Item> WCX = registerGun("wcx", true);
    public static final RegistryObject<Item> DRAGOON = registerGun("dragoon", true);

    public static final RegistryObject<Item> SA110 = registerGun("sa110", false);
    public static final RegistryObject<Item> F2 = registerGun("f2rifle", true);


    public static final RegistryObject<Item> KOBRA_SCOPE = ITEMS.register("reddot", () -> new ScopeItem(Scope.create(0.15F, 1.75F, GunModifiers.SLOW_ADS).viewFinderOffset(0.4), genericProperties));
    public static final RegistryObject<Item> LONGRANGE_8x_SCOPE = ITEMS.register("highscope", () -> new ScopeItem(Scope.create(0.435F, 1.930F, RE8GunItem.LONGRANGE_8x_SCOPE_ADS).viewFinderOffset(0.595), genericProperties));

    public static final RegistryObject<Item> RECOIL = ITEMS.register("recoil", () -> {
        return new BarrelItem(Barrel.create(1.5F, new IGunModifier[]{GunModifiers.SILENCED, ItemInit.BOOST_DAMAGE}), (new Item.Properties()).stacksTo(1).tab(Main.TUTORIAL_TAB));
    });

    public static final RegistryObject<Item> LONGB = ITEMS.register("longb", () -> {
        return new BarrelItem(Barrel.create(3.5F, new IGunModifier[]{GunModifiers.SILENCED, ItemInit.LONGB_BOOST}), (new Item.Properties()).stacksTo(1).tab(Main.TUTORIAL_TAB));
    });

    public static final RegistryObject<Item> IMPROVE_STOCK = ITEMS.register("improvestock", () -> {
        return new StockItem(Stock.create(new IGunModifier[]{ItemInit.BETTER_BETTER_CONTROL}),
                (new Item.Properties()).stacksTo(1).tab(Main.TUTORIAL_TAB), false);
    });

    public static final RegistryObject<Item> FOREGRIP = ITEMS.register("foregrip", () -> {
        return new UnderBarrelItem(UnderBarrel.create(new IGunModifier[]{ItemInit.BETTER_RECOIL}),
                (new Item.Properties()).stacksTo(1).tab(Main.TUTORIAL_TAB));
    });

    public static final RegistryObject<Item> DRUMMAGAZINE = ITEMS.register("drummagazine", () -> {
        return new UnderBarrelItem(UnderBarrel.create(new IGunModifier[]{ItemInit.ADD_AMMO}),
                (new Item.Properties()).stacksTo(1).tab(Main.TUTORIAL_TAB));
    });

    public static final RegistryObject<Item> SNIPERAMMO = registerAmmo("sniperammo");
    public static final RegistryObject<Item> RIFLEAMMO = registerAmmo("rifleammo");
    public static final RegistryObject<Item> HANDAMMO = registerAmmo("handammo");
    public static final RegistryObject<Item> M1851B = registerAmmo("m1851b");
    public static final RegistryObject<Item> M1879B = registerAmmo("m1897b");
    //public static final RegistryObject<Item> GM79B = registerGrenade("gm79b");



    private static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    private static RegistryObject<Item> registerGun(String name, boolean canColor) {
        return ITEMS.register(name, () -> new RE8GunItem(genericProperties, canColor));
    }

    private static RegistryObject<Item> registerAmmo(String name) {
        return ITEMS.register(name, () -> new AmmoItem(new Item.Properties().tab(Main.TUTORIAL_TAB)));
    }

    private static RegistryObject<Item> registerGrenade(String name) {
        return ITEMS.register(name, () -> new RE8GrenadeItem(new Item.Properties().tab(Main.TUTORIAL_TAB),80));
    }

    public static final IGunModifier BOOST_DAMAGE = new IGunModifier() {
        public float modifyProjectileDamage(float damage) {
            return damage * 1.25F;
        }

        public float modifyFireSoundVolume(float volume) {
            return volume*1.15f;
        }
    };

    public static final IGunModifier LONGB_BOOST = new IGunModifier() {
        public float modifyProjectileDamage(float damage) {
            return damage * 1.25F;
        }

        public float modifyFireSoundVolume(float volume) {
            return volume*0.75f;
        }
    };

    public static final IGunModifier BETTER_BETTER_CONTROL = new IGunModifier() {
        public float recoilModifier() {
            return 0.25F;
        }

        public float kickModifier() {
            return 0.8F;
        }

        public float modifyProjectileSpread(float spread) {
            return spread * 0.5F;
        }

        public double modifyAimDownSightSpeed(double speed) {
            return speed * 1.1D;
        }
    };

    public static final IGunModifier BETTER_RECOIL = new IGunModifier() {
        public float recoilModifier() {
            return 0.75F;
        }

        public double modifyAimDownSightSpeed(double speed) {
            return speed * 1.35;
        }

        public float modifyProjectileSpread(float spread) {
            return spread * 0.7F;
        }
    };

    public static final IGunModifier ADD_AMMO = new IGunModifier() {
        public int modifyFireRate(int rate) {
            return (int)(rate * 0.5);
        }

        public float criticalChance() {
            return 0.6F;
        }

    };

}
