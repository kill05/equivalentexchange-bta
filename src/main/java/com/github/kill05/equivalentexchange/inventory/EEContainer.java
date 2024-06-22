package com.github.kill05.equivalentexchange.inventory;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public abstract class EEContainer extends Container {

	public static final int SLOT_SIZE = 18;

	private final int alignOffsetX;
	private final int alignOffsetY;

	protected EEContainer(int alignOffsetX, int alignOffsetY) {
		this.alignOffsetX = alignOffsetX;
		this.alignOffsetY = alignOffsetY;
	}

	// Call this after creating all the gui slots, else hotkey inventory actions get messed up
	public void addPlayerInventory(EntityPlayer player) {
		addPlayerInventory(player, 0, 140);
	}

	public void addPlayerInventory(EntityPlayer player, int offsetX, int offsetY) {
		IInventory playerInv = player.inventory;
		for (int slotY = 0; slotY < 3; ++slotY) {
			for (int slotX = 0; slotX < 9; ++slotX) {
				this.addSlot(new Slot(playerInv, slotY * 9 + slotX + 9, alignX(slotX) + offsetX, alignY(slotY) + offsetY));
			}
		}

		for (int slotX = 0; slotX < 9; ++slotX) {
			this.addSlot(new Slot(playerInv, slotX, alignX(slotX) + offsetX, alignY(3f) + 4 + offsetY));
		}
	}


	public void addAlignedSlot(IInventory inv, int id, float x, float y) {
		addSlot(new Slot(inv, id, alignX(x), alignY(y)));
	}

	public void addAlignedSlot(Slot slot, float x, float y) {
		slot.xDisplayPosition = alignX(x);
		slot.yDisplayPosition = alignY(y);
		addSlot(slot);
	}

	public int alignX(float x) {
		return (int) (x * SLOT_SIZE + 8) + alignOffsetX;
	}

	public int alignY(float y) {
		return (int) (y * SLOT_SIZE) + alignOffsetY;
	}

}
