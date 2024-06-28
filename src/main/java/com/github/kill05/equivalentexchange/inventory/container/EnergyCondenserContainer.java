package com.github.kill05.equivalentexchange.inventory.container;

import com.github.kill05.equivalentexchange.inventory.slot.CondenserFilterSlot;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class EnergyCondenserContainer extends AlchemicalChestContainer {

	private final EnergyCondenserTile tile;

	public EnergyCondenserContainer(EntityPlayer player, EnergyCondenserTile tile) {
		super(tile.getInputInventory(), 13, 4, 26);
		this.tile = tile;

		addSlot(new CondenserFilterSlot(tile, 12, 6));
		addPlayerInventory(player, 36, 128);
	}


	@Override
	public List<Integer> getMoveSlots(InventoryAction action, Slot slot, int i, EntityPlayer entityPlayer) {
		if(slot instanceof CondenserFilterSlot) return null;
		return super.getMoveSlots(action, slot, i, entityPlayer);
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int i, EntityPlayer entityPlayer) {
		if(slot instanceof CondenserFilterSlot) return null;

		if (slot.id < inventory.getSizeInventory()) {
			return getSlots(inventory.getSizeInventory() + 1, 36, true);
		}

		return super.getTargetSlots(action, slot, i, entityPlayer);

	}

	public EnergyCondenserTile getTile() {
		return tile;
	}


}
