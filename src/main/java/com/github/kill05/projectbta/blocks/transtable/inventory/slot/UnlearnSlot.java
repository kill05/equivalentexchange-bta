package com.github.kill05.projectbta.blocks.transtable.inventory.slot;

import com.github.kill05.projectbta.blocks.transtable.inventory.TransmutationTableContainer;
import com.github.kill05.projectbta.blocks.transtable.inventory.TransmutationTableGui;
import com.github.kill05.projectbta.emc.EmcKey;
import com.github.kill05.projectbta.emc.ProjectPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;

public class UnlearnSlot extends Slot {

	private final TransmutationTableContainer container;

	public UnlearnSlot(TransmutationTableContainer container, int id, int x, int y) {
		super(container.getInventory(), id, x, y);
		this.container = container;
	}

	@Override
	public void putStack(ItemStack itemstack) {
		super.putStack(itemstack);

		ProjectPlayer player = (ProjectPlayer) container.getPlayer();
		if (player.unlearnItem(new EmcKey(itemstack))) {
			TransmutationTableGui.displayGuiMessage(container, "Unlearned!");
		}
	}
}
