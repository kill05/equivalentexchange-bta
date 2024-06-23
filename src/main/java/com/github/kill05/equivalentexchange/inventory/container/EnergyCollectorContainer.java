package com.github.kill05.equivalentexchange.inventory.container;

import com.github.kill05.equivalentexchange.inventory.slot.collector.CollectorInputSlot;
import com.github.kill05.equivalentexchange.inventory.slot.collector.CollectorOutputSlot;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class EnergyCollectorContainer extends EEContainer {

	private final EnergyCollectorTile tile;

	public EnergyCollectorContainer(EntityPlayer player, EnergyCollectorTile tile) {
		super(0, 0);
		this.tile = tile;

		for(int i = 0; i < 12; i++) {
			addSlot(new CollectorInputSlot(tile, i, 18 + (i % 3) * 18, 8 + (i / 3) * 18));
		}

		addSlot(new CollectorInputSlot(tile, 12, 140, 58));
		addSlot(new CollectorInputSlot(tile, 13, 140, 13));
		addSlot(new CollectorOutputSlot(tile, 14, 169, 58));

		addPlayerInventory(player, 12, 84);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	public EnergyCollectorTile getTile() {
		return tile;
	}
}
