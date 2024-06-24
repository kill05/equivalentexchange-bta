package com.github.kill05.equivalentexchange.inventory.slot.collector;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class CollectorInputSlot extends Slot {

	public CollectorInputSlot(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}

	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		if (itemstack == null) return false;
		return EnergyCollectorTile.CONVERSION_MAP.containsKey(new EmcKey(itemstack)) || itemstack.getItem() instanceof IItemEmcHolder;
	}
}
