package com.github.kill05.equivalentexchange.inventory.slot.select;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;
import org.jetbrains.annotations.Nullable;

public abstract class SelectItemSlot extends Slot {

	public SelectItemSlot(int x, int y) {
		super(null, 0, x, y);
	}


	public abstract void selectItem(@Nullable ItemStack itemStack);

	public abstract @Nullable ItemStack getSelectedItem();


	@Override
	public boolean canPutStackInSlot(ItemStack itemStack) {
		selectItem(itemStack);
		return false;
	}

	@Override
	public boolean allowItemInteraction() {
		selectItem(null);
		return true;
	}

	@Override
	public ItemStack getStack() {
		return getSelectedItem();
	}


	@Override
	public boolean enableDragAndPickup() {
		return false;
	}

	@Override
	public IInventory getInventory() {
		return null;
	}

	@Override
	public void onSlotChanged() {

	}

	@Override
	public ItemStack decrStackSize(int i) {
		return null;
	}
}
