package com.github.kill05.projectbta.block.transtable.inventory.slot;

import com.github.kill05.projectbta.ProjectBTA;
import com.github.kill05.projectbta.ProjectPlayer;
import com.github.kill05.projectbta.block.transtable.BurnResult;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableContainer;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableGui;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class BurnSlot extends Slot {


	public BurnSlot(TransmutationTableContainer container, int x, int y) {
		super(new InventoryImpl(container), 0, x, y);
	}


	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return ProjectBTA.getEmcValue(itemstack) != null;
	}

	private record InventoryImpl(TransmutationTableContainer container) implements IInventory {

		@Override
		public int getSizeInventory() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int i) {
			return null;
		}

		@Override
		public ItemStack decrStackSize(int i, int j) {
			return null;
		}

		@Override
		public void setInventorySlotContents(int i, ItemStack itemStack) {
			ProjectPlayer player = (ProjectPlayer) container.getPlayer();
			if(player.burnItem(itemStack) != BurnResult.SUCCESS_LEARNED) return;
			TransmutationTableGui.displayGuiMessage(container, "Learned!");
		}

		@Override
		public String getInvName() {
			return null;
		}

		@Override
		public int getInventoryStackLimit() {
			return 64;
		}

		@Override
		public void onInventoryChanged() {

		}

		@Override
		public boolean canInteractWith(EntityPlayer entityPlayer) {
			return true;
		}

		@Override
		public void sortInventory() {

		}
	}

}
