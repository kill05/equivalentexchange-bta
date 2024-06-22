package com.github.kill05.equivalentexchange.inventory.slot;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IEmcHolder;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class ChargeSlot extends Slot {

	private final IEmcHolder holder;

	public ChargeSlot(IInventory inventory, IEmcHolder holder, int id, int x, int y) {
		super(inventory, id, x, y);
		this.holder = holder;
	}

	@Override
	public void putStack(ItemStack itemstack) {
		super.putStack(itemstack);
		if(itemstack == null || !(itemstack.getItem() instanceof IItemEmcHolder itemHolder)) return;

		EmcTransaction transaction = itemHolder.addEmc(itemstack, holder.getEmc());

		if(transaction.success()) {
			holder.removeEmc(transaction.transferredAmount());
		}
	}
}
