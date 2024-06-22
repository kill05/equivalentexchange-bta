package com.github.kill05.equivalentexchange.items;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.utils.NbtUtils;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public class EmcItem extends Item implements IItemEmcHolder {

	private final long maxAmount;

	public EmcItem(String name, int id, long maxEmcAmount) {
		super(name, id);
		setMaxStackSize(1);
		this.maxAmount = maxEmcAmount;
	}

	public EmcItem(int id, long maxAmount) {
		super(id);
		this.maxAmount = maxAmount;
	}


	@Override
	public long getMaxEmc(ItemStack itemStack) {
		return maxAmount;
	}

	@Override
	public long getEmc(ItemStack itemStack) {
		return NbtUtils.getMainCompound(itemStack.getData(), false).getLong("emc");
	}

	@Override
	public EmcTransaction setEmc(ItemStack itemStack, long emc, boolean ignoreLimit) {
		return EmcTransaction.transactionSetEmc(emc, getMaxEmc(itemStack), ignoreLimit, aLong ->
			NbtUtils.getMainCompound(itemStack.getData(), true).putLong("emc", aLong)
		);
	}
}
