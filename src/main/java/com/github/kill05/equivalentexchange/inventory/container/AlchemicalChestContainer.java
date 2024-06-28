package com.github.kill05.equivalentexchange.inventory.container;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class AlchemicalChestContainer extends EEContainer {

	protected final IInventory inventory;

	protected AlchemicalChestContainer(IInventory inventory, int slotPerRow, int offsetX, int offsetY) {
		super(offsetX, offsetY);
		this.inventory = inventory;

		for(int i = 0; i < this.inventory.getSizeInventory(); i++) {
			int x = i % slotPerRow;
			int y = i / slotPerRow;
			addSlot(createSlot(i, alignX(x), alignY(y)));
		}
	}

	public AlchemicalChestContainer(EntityPlayer player, IInventory inventory) {
		this(inventory, 13, 4, 5);
		addPlayerInventory(player, 36, 147);
	}

	protected Slot createSlot(int index, int x, int y) {
		return new Slot(this.inventory, index, x, y);
	}


	@Override
	public List<Integer> getMoveSlots(InventoryAction action, Slot slot, int i, EntityPlayer entityPlayer) {
		//todo: check impl
		int size = inventory.getSizeInventory();
		if (slot.id >= 0 && slot.id < size) {
			return this.getSlots(0, size, false);
		}
		if (action == InventoryAction.MOVE_ALL) {
			if (slot.id >= size && slot.id < size + 27) {
				return this.getSlots(size, 27, false);
			}
			if (slot.id >= size + 27 && slot.id < size + 36) {
				return this.getSlots(size + 27, 9, false);
			}
		} else if (slot.id >= size && slot.id < size + 36) {
			return this.getSlots(size, 36, false);
		}
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction action, Slot slot, int i, EntityPlayer entityPlayer) {
		if(slot.id >= inventory.getSizeInventory()) {
			return getSlots(0, inventory.getSizeInventory(), false);
		}

		return getSlots(inventory.getSizeInventory(), 36, true);
	}

	public IInventory getInventory() {
		return inventory;
	}
}
