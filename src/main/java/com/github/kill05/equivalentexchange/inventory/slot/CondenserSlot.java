package com.github.kill05.equivalentexchange.inventory.slot;

import com.github.kill05.equivalentexchange.emc.holder.IEmcHolder;
import net.minecraft.core.item.ItemStack;

public class CondenserSlot extends BurnSlot {

	public CondenserSlot(IEmcHolder holder, int x, int y) {
		super(holder, x, y);
	}

	@Override
	public boolean canPutStackInSlot(ItemStack itemstack) {
		return true;
	}

	@Override
	public void putStack(ItemStack itemstack) {
		super.putStack(itemstack);
	}
}
