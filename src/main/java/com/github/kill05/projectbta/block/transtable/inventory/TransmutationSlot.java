package com.github.kill05.projectbta.block.transtable.inventory;

import com.github.kill05.projectbta.EmcRegistry;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class TransmutationSlot extends Slot {

	private final TransmutationTableContainer container;

	public TransmutationSlot(TransmutationTableContainer container, int index) {
		super(new InventoryImpl(container, index), 0, 0, 0);
		this.container = container;
	}

	private static class InventoryImpl implements IInventory {

		private final TransmutationTableContainer container;
		private final int index;

		private InventoryImpl(TransmutationTableContainer container, int index) {
			this.container = container;
			this.index = index;
		}

		@Override
		public int getSizeInventory() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int unused) {
			EmcRegistry instance = EmcRegistry.getInstance();
			List<ItemStack> sortedItems = instance.getSortedItems();
			int totalIndex = container.getPage() * TransmutationTableContainer.slotCoords.length + this.index;

			if(totalIndex >= sortedItems.size()) return null;
			return sortedItems.get(totalIndex);
		}

		@Override
		public ItemStack decrStackSize(int i, int j) {
			return null;
		}

		@Override
		public void setInventorySlotContents(int i, ItemStack itemStack) {

		}

		@Override
		public String getInvName() {
			return null;
		}

		@Override
		public int getInventoryStackLimit() {
			return 0;
		}

		@Override
		public void onInventoryChanged() {

		}

		@Override
		public boolean canInteractWith(EntityPlayer entityPlayer) {
			return false;
		}

		@Override
		public void sortInventory() {

		}
	}
}
