package com.github.kill05.projectbta.blocks.transtable.inventory.slot;

import com.github.kill05.projectbta.blocks.transtable.inventory.TransmutationTableContainer;
import net.minecraft.core.player.inventory.slot.Slot;

public class ChargeSlot extends Slot {

	private final TransmutationTableContainer container;

	public ChargeSlot(TransmutationTableContainer container, int id, int x, int y) {
		super(container.getInventory(), id, x, y);
		this.container = container;
	}
}
