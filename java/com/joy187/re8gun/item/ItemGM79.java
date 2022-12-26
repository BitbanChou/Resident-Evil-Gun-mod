//package com.joy187.re8gun.item;
//
//import com.joy187.re8gun.init.ItemInit;
//import com.mrcrayfish.guns.client.GunItemStackRenderer;
//import com.mrcrayfish.guns.client.KeyBinds;
//import com.mrcrayfish.guns.common.Gun;
//import com.mrcrayfish.guns.common.NetworkGunManager;
//import com.mrcrayfish.guns.enchantment.EnchantmentTypes;
//import com.mrcrayfish.guns.util.GunEnchantmentHelper;
//import com.mrcrayfish.guns.util.GunModifierHelper;
//import net.minecraft.ChatFormatting;
//import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
//import net.minecraft.core.NonNullList;
//import net.minecraft.nbt.CompoundTag;
//import net.minecraft.network.chat.Component;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.item.CreativeModeTab;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.TooltipFlag;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.level.Level;
//import net.minecraftforge.client.extensions.common.IClientItemExtensions;
//import net.minecraftforge.registries.ForgeRegistries;
//
//import javax.annotation.Nullable;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//import java.util.WeakHashMap;
//import java.util.function.Consumer;
//
//public class ItemGM79 extends RE8GunItem{
//
//    public ItemGM79(Properties properties, boolean canColor) {
//        super(properties, canColor);
//    }
//
//    private WeakHashMap<CompoundTag, Gun> modifiedGunCache = new WeakHashMap();
//    private Gun gun = new Gun();
//
//    public void setGun(NetworkGunManager.Supplier supplier) {
//        this.gun = supplier.getGun();
//    }
//
//    public Gun getGun() {
//        return this.gun;
//    }
//
//    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flag) {
//        Gun modifiedGun = this.getModifiedGun(stack);
//        //Item ammo = (Item) ForgeRegistries.ITEMS.getValue(modifiedGun.getProjectile().getItem());
//        Item ammo = ItemInit.GM79B.get();
//        if (ammo != null) {
//            tooltip.add(Component.translatable("info.cgm.ammo_type", new Object[]{Component.translatable(ammo.getDescriptionId()).withStyle(ChatFormatting.WHITE)}).withStyle(ChatFormatting.GRAY));
//        }
//
//        String additionalDamageText = "";
//        CompoundTag tagCompound = stack.getTag();
//        float additionalDamage;
//        if (tagCompound != null && tagCompound.contains("AdditionalDamage", 99)) {
//            additionalDamage = tagCompound.getFloat("AdditionalDamage");
//            additionalDamage += GunModifierHelper.getAdditionalDamage(stack);
//            ChatFormatting var10000;
//            if (additionalDamage > 0.0F) {
//                var10000 = ChatFormatting.GREEN;
//                additionalDamageText = var10000 + " +" + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format((double)additionalDamage);
//            } else if (additionalDamage < 0.0F) {
//                var10000 = ChatFormatting.RED;
//                additionalDamageText = var10000 + " " + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format((double)additionalDamage);
//            }
//        }
//
//        additionalDamage = modifiedGun.getProjectile().getDamage();
//        additionalDamage = GunModifierHelper.getModifiedProjectileDamage(stack, additionalDamage);
//        additionalDamage = GunEnchantmentHelper.getAcceleratorDamage(stack, additionalDamage);
//        Object[] var10002 = new Object[1];
//        ChatFormatting var10005 = ChatFormatting.WHITE;
//        var10002[0] = var10005 + ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format((double)additionalDamage) + additionalDamageText;
//        tooltip.add(Component.translatable("info.cgm.damage", var10002).withStyle(ChatFormatting.GRAY));
//        if (tagCompound != null) {
//            if (tagCompound.getBoolean("IgnoreAmmo")) {
//                tooltip.add(Component.translatable("info.cgm.ignore_ammo").withStyle(ChatFormatting.AQUA));
//            } else {
//                int ammoCount = tagCompound.getInt("AmmoCount");
//                tooltip.add(Component.translatable("info.cgm.ammo", new Object[]{ChatFormatting.WHITE.toString() + ammoCount + "/" + GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun)}).withStyle(ChatFormatting.GRAY));
//            }
//        }
//
//        tooltip.add(Component.translatable("info.cgm.attachment_help", new Object[]{KeyBinds.KEY_ATTACHMENTS.getTranslatedKeyMessage().getString().toUpperCase(Locale.ENGLISH)}).withStyle(ChatFormatting.YELLOW));
//    }
//
//    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
//        return true;
//    }
//
//    public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> stacks) {
//        if (this.allowedIn(group)) {
//            ItemStack stack = new ItemStack(this);
//            stack.getOrCreateTag().putInt("AmmoCount", this.gun.getGeneral().getMaxAmmo());
//            stacks.add(stack);
//        }
//
//    }
//
//    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
//        return slotChanged;
//    }
//
//    public boolean isBarVisible(ItemStack stack) {
//        CompoundTag tagCompound = stack.getOrCreateTag();
//        Gun modifiedGun = this.getModifiedGun(stack);
//        return !tagCompound.getBoolean("IgnoreAmmo") && tagCompound.getInt("AmmoCount") != GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun);
//    }
//
//    public int getBarWidth(ItemStack stack) {
//        CompoundTag tagCompound = stack.getOrCreateTag();
//        Gun modifiedGun = this.getModifiedGun(stack);
//        return (int)(13.0D * ((double)tagCompound.getInt("AmmoCount") / (double)GunEnchantmentHelper.getAmmoCapacity(stack, modifiedGun)));
//    }
//
//    public int getBarColor(ItemStack stack) {
//        return (Integer) Objects.requireNonNull(ChatFormatting.YELLOW.getColor());
//    }
//
//    public Gun getModifiedGun(ItemStack stack) {
//        CompoundTag tagCompound = stack.getTag();
//        return tagCompound != null && tagCompound.contains("Gun", 10) ? (Gun)this.modifiedGunCache.computeIfAbsent(tagCompound, (item) -> {
//            if (tagCompound.getBoolean("Custom")) {
//                return Gun.create(tagCompound.getCompound("Gun"));
//            } else {
//                Gun gunCopy = this.gun.copy();
//                gunCopy.deserializeNBT(tagCompound.getCompound("Gun"));
//                return gunCopy;
//            }
//        }) : this.gun;
//    }
//
//    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
//        if (enchantment.category == EnchantmentTypes.SEMI_AUTO_GUN) {
//            Gun modifiedGun = this.getModifiedGun(stack);
//            return !modifiedGun.getGeneral().isAuto();
//        } else {
//            return super.canApplyAtEnchantingTable(stack, enchantment);
//        }
//    }
//
//    public boolean isEnchantable(ItemStack stack) {
//        return this.getMaxStackSize(stack) == 1;
//    }
//
//    public int getEnchantmentValue() {
//        return 5;
//    }
//
//    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
//        consumer.accept(new IClientItemExtensions() {
//            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
//                return new GunItemStackRenderer();
//            }
//        });
//    }
//
//}
