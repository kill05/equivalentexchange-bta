package com.github.kill05.equivalentexchange.inventory.slot.collector;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class CollectorOutputSlot extends Slot {

	public CollectorOutputSlot(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return false;
	}
}
