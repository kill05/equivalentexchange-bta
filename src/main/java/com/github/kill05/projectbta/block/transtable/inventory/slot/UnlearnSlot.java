package com.github.kill05.projectbta.block.transtable.inventory.slot;

import com.github.kill05.projectbta.ProjectPlayer;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableContainer;
import com.github.kill05.projectbta.block.transtable.inventory.TransmutationTableGui;
import com.github.kill05.projectbta.registry.EmcKey;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryBasic;
import net.minecraft.core.player.inventory.slot.Slot;

public class UnlearnSlot extends Slot {

	public UnlearnSlot(TransmutationTableContainer container, int x, int y) {
		super(new InventoryBasic(null, 1) {
			@Override
			public void setInventorySlotContents(int i, ItemStack itemstack) {
				ProjectPlayer player = (ProjectPlayer) container.getPlayer();
				if (player.unlearnItem(new EmcKey(itemstack))) {
					TransmutationTableGui.displayGuiMessage(container, "Unlearned!");
				}

				super.setInventorySlotContents(i, itemstack);
			}
		}, 0, x, y);
	}


}
