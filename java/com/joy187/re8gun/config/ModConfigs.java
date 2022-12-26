package com.joy187.re8gun.config;

import com.mrcrayfish.guns.Config;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public final class ModConfigs {

    public static final ForgeConfigSpec clientConfig;
    public static final ModConfigs.Client CLIENT;
    public static final ForgeConfigSpec commonSpec;
    public static final ModConfigs.Common COMMON;

    static {
        final Pair<Client, ForgeConfigSpec> clientConfigPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientConfig = clientConfigPair.getRight();
        CLIENT = clientConfigPair.getLeft();

        Pair<Common, ForgeConfigSpec> commonSpecPair = (new ForgeConfigSpec.Builder()).configure(Common::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

    }

    public static class Client {
        public static ForgeConfigSpec.BooleanValue enableGunEnchantmentGlint;

        public Client(ForgeConfigSpec.Builder builder) {
            builder.push("client");
            {
                enableGunEnchantmentGlint = builder
                        .comment("If true, renders enchanted guns from Additional Guns with the enchantment glint. True by default.")
                        .translation("config.re8gun.client.enable_gun_enchantment_glint")
                        .define("enableGunEnchantmentGlint", true);
            }
            builder.pop();
        }
    }
    public static class Common {
        public final ModConfigs.Grenades grenade;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("common");
            this.grenade = new ModConfigs.Grenades(builder);
            builder.pop();
        }
    }

    public static class Grenades {
        public final ForgeConfigSpec.DoubleValue explosionRadius;

        public Grenades(ForgeConfigSpec.Builder builder) {
            builder.comment("Properties relating to grenades").push("grenade");
            this.explosionRadius = builder.comment("The explosion radius of grenade.").defineInRange("explosionRadius", 4.5D, 0.0D, 2.0D);
            builder.pop();
        }
    }

}

