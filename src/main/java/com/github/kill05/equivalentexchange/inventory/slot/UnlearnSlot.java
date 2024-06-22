package com.github.kill05.equivalentexchange.inventory.slot;

import com.github.kill05.equivalentexchange.inventory.container.TransmutationTableContainer;
import com.github.kill05.equivalentexchange.inventory.gui.TransmutationTableGui;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.holder.IPlayerEmcHolder;
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

		IPlayerEmcHolder player = (IPlayerEmcHolder) container.getPlayer();
		if (player.unlearnItem(new EmcKey(itemstack))) {
			TransmutationTableGui.displayGuiMessage("Unlearned!");
		}
	}
}
