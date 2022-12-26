package com.joy187.re8gun.block;


import com.joy187.re8gun.init.BlockEntityInit;
import com.mrcrayfish.guns.blockentity.SyncedBlockEntity;
import com.mrcrayfish.guns.blockentity.inventory.IStorageBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public class RE8WorkbenchBlockEntity extends SyncedBlockEntity implements IStorageBlock {
    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);;

    public RE8WorkbenchBlockEntity(BlockPos pos, BlockState state) {
        super(BlockEntityInit.RE8WORKBENCHENTITY.get(), pos, state);
    }

    public NonNullList<ItemStack> getInventory() {
        return this.inventory;
    }

    protected void saveAdditional(CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, this.inventory);
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, this.inventory);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return index != 0 || stack.getItem() instanceof DyeItem && ((ItemStack)this.inventory.get(index)).getCount() < 1;
    }

    public boolean stillValid(Player player) {
        return this.level.getBlockEntity(this.worldPosition) == this && player.distanceToSqr((double)this.worldPosition.getX() + 0.5D, (double)this.worldPosition.getY() + 0.5D, (double)this.worldPosition.getZ() + 0.5D) <= 64.0D;
    }

    public Component getDisplayName() {
        return Component.translatable("container.re8gun.re8workbench");
    }

    @Nullable
    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
        return new RE8WorkbenchContainer(windowId, playerInventory, this);
    }

}
//public class RE8WorkbenchTileEntity extends SyncedBlockEntity implements IStorageBlock
//{
//    private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);
//
//    public RE8WorkbenchTileEntity()
//    {
//        super(BlockEntityInit.RE8WORKBENCHENTITY.get());
//    }
//
//    @Override
//    public NonNullList<ItemStack> getInventory()
//    {
//        return this.inventory;
//    }
//
//    @Override
//    public void saveAdditional(CompoundTag compound)
//    {
//        ItemStackHelper.saveAllItems(compound, this.inventory);
//        return super.write(compound);
//    }
//
//    @Override
//    public void load(BlockState state, CompoundTag compound)
//    {
//        super.load(state, compound);
//        ItemStackHelper.loadAllItems(compound, this.inventory);
//    }
//
//    @Override
//    public boolean isItemValidForSlot(int index, ItemStack stack)
//    {
//        return index != 0 || (stack.getItem() instanceof DyeItem && this.inventory.get(index).getCount() < 1);
//    }
//
//    @Override
//    public boolean isUsableByPlayer(PlayerEntity player)
//    {
//        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5, this.pos.getY() + 0.5, this.pos.getZ() + 0.5) <= 64.0;
//    }
//
//    @Override
//    public ITextComponent getDisplayName()
//    {
//        return new TranslationTextComponent("container.tac.workbench");
//    }
//
//    @Nullable
//    @Override
//    public Container createMenu(int windowId, PlayerInventory playerInventory, PlayerEntity playerEntity)
//    {
//        return new RE8WorkbenchContainer(windowId, playerInventory, this);
//    }
//}
