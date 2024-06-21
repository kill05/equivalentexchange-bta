package com.github.kill05.projectbta.block.transtable.inventory.slot;

import com.github.kill05.projectbta.ProjectPlayer;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableContainer;
import com.github.kill05.projectbta.registry.EmcKey;
import com.github.kill05.projectbta.registry.EmcRegistry;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class TransmuteSlot extends Slot {

	private final TransmutationTableContainer container;

	public TransmuteSlot(TransmutationTableContainer container, int index, int x, int y) {
		super(new InventoryImpl(container, index), 0, x, y);
		this.container = container;
	}

	public ItemStack transmuteStack(int amount, boolean removeEmc) {
		EmcKey key = getKey();
		if(key == null) return null;

		ProjectPlayer player = (ProjectPlayer) container.getPlayer();
		long cost = key.emcValue();

		amount = (int) Math.min(amount, player.getEmc() / cost);
		if(removeEmc) player.removeEmc(amount * cost);

		return key.itemStack(amount);
	}

	public void removeEmc(int itemAmount) {
		EmcKey key = getKey();
		if(key == null) return;
		((ProjectPlayer) container.getPlayer()).removeEmc(key.emcValue() * itemAmount);
	}

	@Override
	public ItemStack decrStackSize(int i) {
		return super.decrStackSize(i);
	}

	@Override
	public boolean enableDragAndPickup() {
		return false;
	}

	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return false;
	}

	public EmcKey getKey() {
		return ((InventoryImpl) inventory).getKey();
	}

	private record InventoryImpl(TransmutationTableContainer container, int index) implements IInventory {

		public EmcKey getKey() {
			EmcRegistry registry = EmcRegistry.getInstance();
			List<EmcKey> sortedKeys = registry.getSortedKeys();
			long playerEmc = ((ProjectPlayer) container.getPlayer()).getEmc();
			if(playerEmc == 0) return null;

			int offset = 0;
			while (true) {
				if(offset >= sortedKeys.size()) return null;
				EmcKey emcKey = sortedKeys.get(offset);
				if(playerEmc >= emcKey.emcValue()) break;
				offset++;
			}

			int totalIndex = container.getPage() * TransmutationTableContainer.transmuteSlots.length + this.index + offset;

			if (totalIndex >= sortedKeys.size()) return null;
			return sortedKeys.get(totalIndex);
		}

		@Override
		public ItemStack getStackInSlot(int slot) {
			EmcKey key = getKey();
			return key != null ? key.itemStack() : null;
		}

		@Override
		public ItemStack decrStackSize(int slot, int amount) {
			return null;
		}

		@Override
		public int getSizeInventory() {
			return 1;
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
