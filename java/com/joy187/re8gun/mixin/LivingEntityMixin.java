package com.joy187.re8gun.mixin;

import com.mrcrayfish.guns.Config;
import com.mrcrayfish.guns.entity.DamageSourceProjectile;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public class LivingEntityMixin {
    private DamageSource source;

    public LivingEntityMixin() {
    }

    @Inject(
            method = {"hurt"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"
            )}
    )
    private void capture(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        this.source = source;
    }

    @ModifyArg(
            method = {"hurt"},
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;knockback(DDD)V"
            ),
            index = 0
    )
    private double modifyApplyKnockbackArgs(double original) {
        if (this.source instanceof DamageSourceProjectile) {
            if (!(Boolean) Config.COMMON.gameplay.enableKnockback.get()) {
                return 0.0D;
            }

            double strength = (Double)Config.COMMON.gameplay.knockbackStrength.get();
            if (strength > 0.0D) {
                return strength;
            }
        }

        return original;
    }
}