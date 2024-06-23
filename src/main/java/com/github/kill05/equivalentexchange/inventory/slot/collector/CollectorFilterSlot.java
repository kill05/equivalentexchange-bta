package com.github.kill05.equivalentexchange.inventory.slot.collector;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.inventory.slot.select.SelectItemSlot;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import net.minecraft.core.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class CollectorFilterSlot extends SelectItemSlot {

	private final EnergyCollectorTile tile;

	public CollectorFilterSlot(EnergyCollectorTile tile, int x, int y) {
		super(x, y);
		this.tile = tile;
	}

	@Override
	public void selectItem(@Nullable ItemStack itemStack) {
		tile.setFilter(itemStack != null ? new EmcKey(itemStack) : null);
	}

	@Override
	public @Nullable ItemStack getSelectedItem() {
		return tile.getFilter() != null ? tile.getFilter().itemStack() : null;
	}
}