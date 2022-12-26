package com.joy187.re8gun.entity;

import com.joy187.re8gun.config.ModConfigs;
import com.joy187.re8gun.init.EntityInit;
import com.joy187.re8gun.init.ItemInit;
import com.mrcrayfish.guns.Config;
import com.mrcrayfish.guns.common.Gun;
import com.mrcrayfish.guns.entity.GrenadeEntity;
import com.mrcrayfish.guns.entity.ProjectileEntity;
import com.mrcrayfish.guns.entity.ThrowableGrenadeEntity;
import com.mrcrayfish.guns.entity.ThrowableItemEntity;
import com.mrcrayfish.guns.init.ModEntities;
import com.mrcrayfish.guns.init.ModItems;
import com.mrcrayfish.guns.interfaces.IExplosionDamageable;
import com.mrcrayfish.guns.item.GunItem;
import com.mrcrayfish.guns.world.ProjectileExplosion;
import io.netty.buffer.Unpooled;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.network.NetworkHooks;

import java.util.Iterator;

//public class EntityGM79B extends ThrowableItemProjectile{
//    public int explosionPower = 2,id=91088;
//    public double damage;
//    private int ticksInGround;
//    private static final EntityDataAccessor<Integer> ID_EFFECT_COLOR = SynchedEntityData.defineId(EntityGM79B.class, EntityDataSerializers.INT);
//
//
//    public EntityGM79B(EntityType<?> entityIn, Level level) {
//        //super((EntityType<? extends EntitGM79B>) entityIn, level);
//        //this.size(1.0F, 1.0F)
//        super((EntityType<? extends EntityGM79B>) entityIn, level);
//        this.damage = 30D;
//        // TODO Auto-generated constructor stub
//    }
//
//    public EntityGM79B(Level world, LivingEntity entity) {
//        super(EntityInit.GM79B.get(), entity, world);
//        this.damage= 30D;
//    }
//
//
//    @Override
//    protected Item getDefaultItem() {
//        return ItemInit.M1851B.get();
//    }
//
//    protected void onHitEntity(EntityHitResult result) {
//        super.onHitEntity(result);
//        Entity entity = result.getEntity();
//
//        int i = 25;
//        if(entity instanceof Blaze || entity instanceof Ravager){
//            i = 35;
//        }
//        if(entity instanceof WitherSkeleton){
//            i= 45;
//        }
//
//        entity.hurt(DamageSource.thrown(this, this.getOwner()), (float)(i+random.nextFloat()*0.5*this.damage));
//
//    }
//
//
//    protected void onHit(HitResult p_37218_) {
//        super.onHit(p_37218_);
//        if (!this.level.isClientSide) {
//            this.level.broadcastEntityEvent(this, (byte)3);
//            this.level.explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, false, Explosion.BlockInteraction.DESTROY);
//            this.remove(Entity.RemovalReason.KILLED);
//        }
//    }
//
//    @Override
//    public Packet<?> getAddEntityPacket() {
//        FriendlyByteBuf pack = new FriendlyByteBuf(Unpooled.buffer());
//        pack.writeDouble(getX());
//        pack.writeDouble(getY());
//        pack.writeDouble(getZ());
//        pack.writeInt(getId());
//        pack.writeUUID(getUUID());
//
//        return NetworkHooks.getEntitySpawningPacket(this);
//
//    }
//
//    public void addAdditionalSaveData(CompoundTag p_37222_) {
//        super.addAdditionalSaveData(p_37222_);
//        p_37222_.putByte("ExplosionPower", (byte)this.explosionPower);
//    }
//
//    public void readAdditionalSaveData(CompoundTag p_37220_) {
//        super.readAdditionalSaveData(p_37220_);
//        if (p_37220_.contains("ExplosionPower", 99)) {
//            this.explosionPower = p_37220_.getByte("ExplosionPower");
//        }
//
//    }
//
//}
public class EntityGM79B extends ThrowableGrenadeEntity {
    public float rotation;
    public float prevRotation;

    public EntityGM79B(EntityType<? extends ThrowableItemEntity> entityType, Level worldIn) {
        super(entityType, worldIn);
    }

    public EntityGM79B(EntityType<? extends ThrowableItemEntity> entityType, Level world, LivingEntity entity) {
        super(entityType, world, entity);
        this.setShouldBounce(true);
        this.setGravityVelocity(0.05F);
        this.setItem(new ItemStack((ItemLike) ModItems.GRENADE.get()));
        this.setMaxLife(60);
    }

    public EntityGM79B(Level world, LivingEntity entity, int timeLeft) {
        super((EntityType) ModEntities.THROWABLE_GRENADE.get(), world, entity);
        this.setShouldBounce(true);
        this.setGravityVelocity(0.05F);
        this.setItem(new ItemStack((ItemLike)ModItems.GRENADE.get()));
        this.setMaxLife(timeLeft);
    }

    protected void defineSynchedData() {
    }

    public void tick() {
        super.tick();
        this.prevRotation = this.rotation;
        double speed = this.getDeltaMovement().length();
        if (speed > 0.1D) {
            this.rotation = (float)((double)this.rotation + speed * 50.0D);
        }

        if (this.level.isClientSide) {
            this.level.addParticle(ParticleTypes.SMOKE, true, this.getX(), this.getY() + 0.25D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    public void onDeath() {
        GrenadeEntity.createExplosion(this, ((Double)Config.COMMON.grenades.explosionRadius.get()).floatValue(), true);
    }
}

