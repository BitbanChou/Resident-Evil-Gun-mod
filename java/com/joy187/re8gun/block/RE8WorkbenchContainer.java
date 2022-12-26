package com.joy187.re8gun.block;


import com.joy187.re8gun.init.ContainerInit;
import com.mrcrayfish.guns.init.ModContainers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;


public class RE8WorkbenchContainer extends AbstractContainerMenu {
    private RE8WorkbenchBlockEntity workbench;
    private BlockPos pos;

    public RE8WorkbenchContainer(int id, Inventory inv, FriendlyByteBuf extraData) {
        this(id, inv, (RE8WorkbenchBlockEntity) inv.player.level.getBlockEntity(extraData.readBlockPos()));
    }

    public RE8WorkbenchContainer(int windowId, Container playerInventory, RE8WorkbenchBlockEntity workbench) {
        super(ContainerInit.RE8WORKBENCH_MENU.get(), windowId);
        this.workbench = workbench;
        this.pos = workbench.getBlockPos();
        //int offset = !RE8WorkbenchRecipes.isEmpty(workbench.getLevel()) ? 0 : 28;;
        this.addSlot(new Slot(workbench, 0, 174, 18) {
            public boolean mayPlace(ItemStack stack) {
                return stack.getItem() instanceof DyeItem;
            }

            public int getMaxStackSize() {
                return 1;
            }
        });

//        int x;
//        for(x = 0; x < 3; ++x) {
//            for(int y = 0; y < 9; ++y) {
//                this.addSlot(new Slot(playerInventory, y + x * 9 + 9, 8 + y * 18, 102 + x * 18));
//            }
//        }
//
//        for(x = 0; x < 9; ++x) {
//            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 160));
//        }
        for(int y = 0; y < 3; y++)
        {
            for(int x = 0; x < 9; x++)
            {
                this.addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 102 + y * 18));
            }
        }

        for(int x = 0; x < 9; x++)
        {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 160));
        }
    }

    public boolean stillValid(Player playerIn) {
        return this.workbench.stillValid(playerIn);
    }

    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = (Slot)this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if (index == 0) {
                if (!this.moveItemStackTo(slotStack, 1, 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (slotStack.getItem() instanceof DyeItem) {
                if (!this.moveItemStackTo(slotStack, 0, 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < 28) {
                if (!this.moveItemStackTo(slotStack, 28, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index <= 36 && !this.moveItemStackTo(slotStack, 1, 28, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }

        return stack;
    }

    public RE8WorkbenchBlockEntity getWorkbench() {
        return this.workbench;
    }

    public BlockPos getPos() {
        return this.pos;
    }
}
