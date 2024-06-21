package com.github.kill05.projectbta.inventory.slot;

import com.github.kill05.projectbta.emc.EmcTransaction;
import com.github.kill05.projectbta.emc.holder.IEmcHolder;
import com.github.kill05.projectbta.emc.holder.IItemEmcHolder;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

public class DischargeSlot extends Slot {

	private final IEmcHolder holder;

	public DischargeSlot(IInventory inventory, IEmcHolder holder, int id, int x, int y) {
		super(inventory, id, x, y);
		this.holder = holder;
	}

	@Override
	public void putStack(ItemStack itemstack) {
		super.putStack(itemstack);
		if(itemstack == null || !(itemstack.getItem() instanceof IItemEmcHolder itemHolder)) return;

		EmcTransaction transaction = holder.addEmc(itemHolder.getEmc(itemstack));

		if(transaction.success()) {
			itemHolder.removeEmc(itemstack, transaction.transferredAmount());
		}
	}
}
