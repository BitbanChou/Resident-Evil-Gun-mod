package com.joy187.re8gun.item;

import com.joy187.re8gun.Main;
import com.joy187.re8gun.entity.EntityGM79B;
import com.joy187.re8gun.init.ItemInit;
import com.mrcrayfish.guns.init.ModSounds;
import com.mrcrayfish.guns.item.AmmoItem;
import com.mrcrayfish.guns.item.GrenadeItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import static net.minecraft.world.item.BowItem.getPowerForTime;


public class RE8GrenadeItem extends AmmoItem{
//    public RE8GrenadeItem(Item.Properties name) {
//        super(name);
//    }
    protected int maxCookTime;

    public RE8GrenadeItem(Properties properties, int maxCookTime) {
        super(properties);
        this.maxCookTime = maxCookTime;
    }

    public EntityGM79B createArrow(Level level, ItemStack stack, LivingEntity entityIn) {
        EntityGM79B arrowentity = new EntityGM79B(level, entityIn,80);
        return arrowentity;
    }

    public boolean isInfinite(ItemStack stack, ItemStack bow, net.minecraft.world.entity.player.Player player) {
        int enchant = net.minecraft.world.item.enchantment.EnchantmentHelper.getItemEnchantmentLevel(net.minecraft.world.item.enchantment.Enchantments.INFINITY_ARROWS, bow);
        return enchant <= 0 ? false : this.getClass() == RE8GrenadeItem.class;
    }
}
//public class RE8GrenadeItem extends GrenadeItem {
//    protected int maxCookTime;
//
//    public RE8GrenadeItem(Properties properties, int maxCookTime) {
//        super(properties,80);
//        this.maxCookTime = maxCookTime;
//    }
//
//    public UseAnim getUseAnimation(ItemStack stack) {
//        return UseAnim.BOW;
//    }
//
//    public int getUseDuration(ItemStack stack) {
//        return this.maxCookTime;
//    }
//
//    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
//        if (this.canCook()) {
//            int duration = this.getUseDuration(stack) - count;
//            if (duration == 10) {
//                player.level.playLocalSound(player.getX(), player.getY(), player.getZ(), (SoundEvent) ModSounds.ITEM_GRENADE_PIN.get(), SoundSource.PLAYERS, 1.0F, 1.0F, false);
//            }
//
//        }
//    }
//
//    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
//        ItemStack stack = playerIn.getItemInHand(handIn);
//        playerIn.startUsingItem(handIn);
//        return InteractionResultHolder.consume(stack);
//    }
//
////    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
////        if (this.canCook() && !worldIn.isClientSide()) {
////            if (!(entityLiving instanceof Player) || !((Player)entityLiving).isCreative()) {
////                stack.shrink(1);
////            }
////
////            //EntityGM79B grenade = this.create(worldIn, entityLiving, 0);
////            EntityGM79B grenade = new EntityGM79B(worldIn,entityLiving);
////            //grenade.onDeath();
////            if (entityLiving instanceof Player) {
////                ((Player)entityLiving).awardStat(Stats.ITEM_USED.get(this));
////            }
////        }
////
////        return stack;
////    }
//
////    public void releaseUsing(ItemStack stack, Level worldIn, LivingEntity entityLiving, int timeLeft) {
////        if (!worldIn.isClientSide()) {
////            int duration = this.getUseDuration(stack) - timeLeft;
////            if (duration >= 10) {
////                if (!(entityLiving instanceof Player) || !((Player)entityLiving).isCreative()) {
////                    stack.shrink(1);
////                }
////
////                //EntityGM79B grenade = this.create(worldIn, entityLiving, this.maxCookTime - duration);
////                EntityGM79B grenade = new EntityGM79B(worldIn,entityLiving);
////                grenade.shootFromRotation(entityLiving, entityLiving.getXRot(), entityLiving.getYRot(), 0.0F, Math.min(1.0F, (float)duration / 20.0F), 1.0F);
////                worldIn.addFreshEntity(grenade);
////                this.onThrown(worldIn, grenade);
////                if (entityLiving instanceof Player) {
////                    ((Player)entityLiving).awardStat(Stats.ITEM_USED.get(this));
////                }
////            }
////        }
////
////    }
//
//    public void releaseUsing(ItemStack p_77615_1_, Level p_77615_2_, LivingEntity p_77615_3_, int p_77615_4_) {
//        if (p_77615_3_ instanceof Player) {
//            Player Player = (Player)p_77615_3_;
//            boolean flag = Player.getAbilities().instabuild;
////            ItemStack itemstack = Player.getProjectile(p_77615_1_);
//            ItemStack itemstack = this.findAmmo(Player);
//
//            int i = this.getUseDuration(p_77615_1_) - p_77615_4_;
//            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(p_77615_1_, p_77615_2_, Player, i, !itemstack.isEmpty() || flag);
//            if (i < 0) return;
//
//            if (!itemstack.isEmpty() || flag) {
//                if (itemstack.isEmpty()) {
//                    itemstack = new ItemStack(ItemInit.GM79B.get().asItem());
//                }
//
//                float f = getPowerForTime(i);
//                if (!((double)f < 0.1D)) {
//                    boolean flag1 = Player.getAbilities().instabuild || (itemstack.getItem() instanceof ItemGM79B && ((ItemGM79B)itemstack.getItem()).isInfinite(itemstack, p_77615_1_, Player));
//                    if (!p_77615_2_.isClientSide) {
//                        ItemGM79B arrowitem = (ItemGM79B)(itemstack.getItem() instanceof ItemGM79B ? itemstack.getItem() : ItemInit.GM79B.get().asItem());
//                        EntityGM79B abstractarrowentity = arrowitem.createArrow(p_77615_2_, itemstack, Player);
//                        abstractarrowentity = customArrow(abstractarrowentity);
//                        abstractarrowentity.setItem(itemstack);
//                        abstractarrowentity.shootFromRotation(Player, Player.getXRot(), Player.getYRot(), 0.0F, f * 3.0F, 1.0F);
//                        abstractarrowentity.level.addParticle(ParticleTypes.FLAME, abstractarrowentity.getX(), abstractarrowentity.getY(), abstractarrowentity.getZ(), abstractarrowentity.position().x , abstractarrowentity.position().y, abstractarrowentity.position().z);
//
////                        if (f == 1.0F) {
////                            abstractarrowentity.setCritArrow(true);
////                        }
//
//
//                        if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, p_77615_1_) > 0) {
//                            abstractarrowentity.setSecondsOnFire(100);
//                        }
//
//                        p_77615_1_.hurtAndBreak(1, Player, (p_220009_1_) -> {
//                            p_220009_1_.broadcastBreakEvent(Player.getUsedItemHand());
//                        });
//
//
//                        abstractarrowentity.level.addParticle(ParticleTypes.FLAME, abstractarrowentity.getX(), abstractarrowentity.getY(), abstractarrowentity.getZ(), abstractarrowentity.position().x * -0.2D, 0.1D, abstractarrowentity.position().z * -0.2D);
//                        p_77615_2_.addFreshEntity(abstractarrowentity);
//                        abstractarrowentity.playSound(SoundInit.GM79PRO.get(), 2.5F, 2.5F);
//
//                    }
//
//                    p_77615_2_.playSound((Player)null, Player.getX(), Player.getY(), Player.getZ(), SoundInit.GM79PRO.get(), SoundSource.PLAYERS, 3.0F, 3.0F / (Player.getRandom().nextFloat() * 0.2F + 1.2F) + f * 0.5F);
//                    if (!flag1 && !Player.getAbilities().instabuild) {
//                        itemstack.shrink(1);
//                        if (itemstack.isEmpty()) {
//                            Player.getInventory().removeItem(itemstack);
//                        }
//                    }
//
//                    Player.awardStat(Stats.ITEM_USED.get(this));
//                }
//            }
//        }
//    }
//
//    protected ItemStack findAmmo(Player player)
//    {
//        if (this.isGM79B(player.getItemInHand(InteractionHand.OFF_HAND)))
//        {
//            return player.getItemInHand(InteractionHand.OFF_HAND);
//        }
//        else if (this.isGM79B(player.getItemInHand(InteractionHand.MAIN_HAND)))
//        {
//            return player.getItemInHand(InteractionHand.MAIN_HAND);
//        }
//        else
//        {
//            for (int i = 0; i < player.getInventory().getContainerSize(); ++i)
//            {
//                ItemStack itemstack = player.getInventory().getItem(i);
//
//                if (this.isGM79B(itemstack))
//                {
//                    return itemstack;
//                }
//            }
//
//            return ItemStack.EMPTY;
//        }
//    }
//
////    public EntityGM79B create(Level world, LivingEntity entity, int timeLeft) {
////        return new EntityGM79B(world, entity, timeLeft);
////    }
//    protected boolean isGM79B(ItemStack stack)
//    {
//        return stack.getItem() instanceof ItemGM79B;
//    }
//
//    public boolean canCook() {
//        return true;
//    }
//
//    protected void onThrown(Level world, EntityGM79B entity) {
//    }
//}
