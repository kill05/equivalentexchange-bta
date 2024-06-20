package com.github.kill05.projectbta.block.transtable.inventory;

import com.github.kill05.projectbta.inventory.ProjectContainer;
import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class TransmutationTableContainer extends ProjectContainer {

	public static final int[][] slotCoords = new int[][]{
		new int[]{165, 16}
	};

	private int page;

	public TransmutationTableContainer(EntityPlayer player) {
		super(0, 0);
		for(int i = 0; i < slotCoords.length; i++) {
			addSlot(new TransmutationSlot(this, i));
		}

		addPlayerInventory(player);
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return false;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
}
