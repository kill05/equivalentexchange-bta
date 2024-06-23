package com.github.kill05.equivalentexchange.inventory.slot;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class CondenserFilterSlot extends Slot {

	private final EnergyCondenserTile tile;

	public CondenserFilterSlot(EnergyCondenserTile tile, int x, int y) {
		super(new InventoryImpl(tile), 0, x, y);
		this.tile = tile;
	}

	@Override
	public boolean canPutStackInSlot(ItemStack itemStack) {
		tile.setOutput(itemStack != null ? new EmcKey(itemStack) : null);
		return false;
	}

	@Override
	public ItemStack getStack() {
		EmcKey output = tile.getOutput();
		return output != null ? output.itemStack() : null;
	}

	@Override
	public boolean allowItemInteraction() {
		tile.setOutput(null);
		return super.allowItemInteraction();
	}

	@Override
	public boolean enableDragAndPickup() {
		return false;
	}

	private record InventoryImpl(EnergyCondenserTile tile) implements IInventory {
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

		}

		@Override
		public String getInvName() {
			return null;
		}

		@Override
		public int getInventoryStackLimit() {
			return 1;
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
