package com.github.kill05.equivalentexchange.inventory.slot;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.blocks.BurnResult;
import com.github.kill05.equivalentexchange.inventory.gui.TransmutationTableGui;
import com.github.kill05.equivalentexchange.emc.holder.IEmcHolder;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class BurnSlot extends Slot {

	public BurnSlot(IEmcHolder holder, int x, int y) {
		super(new InventoryImpl(holder), 0, x, y);
	}


	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return EquivalentExchange.getEmcValue(itemstack) != null;
	}

	private record InventoryImpl(IEmcHolder holder) implements IInventory {

		@Override
		public int getSizeInventory() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int i) {
			return null;
		}

		@Override
		public ItemStack decrStackSize(int i, int j) {
			return null;
		}

		@Override
		public void setInventorySlotContents(int i, ItemStack itemStack) {
			if(holder.burnItem(itemStack) != BurnResult.SUCCESS_LEARNED) return;
			TransmutationTableGui.displayGuiMessage("Learned!");
		}

		@Override
		public String getInvName() {
			return null;
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public void onInventoryChanged() {

		}

		@Override
		public boolean canInteractWith(EntityPlayer entityPlayer) {
			return true;
		}

		@Override
		public void sortInventory() {

		}
	}

}
