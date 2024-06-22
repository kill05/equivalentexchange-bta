package com.github.kill05.equivalentexchange.emc.holder;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import net.minecraft.core.item.ItemStack;

public interface IItemEmcHolder {

	long getMaxEmc(ItemStack itemStack);

	long getEmc(ItemStack itemStack);

	default EmcTransaction setEmc(ItemStack itemStack, long emc) {
		return setEmc(itemStack, emc, false);
	}

	EmcTransaction setEmc(ItemStack itemStack, long emc, boolean ignoreLimit);

	default EmcTransaction addEmc(ItemStack itemStack, long emc) {
		return addEmc(itemStack, emc, false);
	}

	default EmcTransaction addEmc(ItemStack itemStack, long emc, boolean ignoreLimit) {
		return EmcTransaction.transactionModifyEmc(getEmc(itemStack), emc, getMaxEmc(itemStack), ignoreLimit, aLong ->
			setEmc(itemStack, aLong, true)
		);
	}

	default EmcTransaction removeEmc(ItemStack itemStack, long emc) {
		return removeEmc(itemStack, emc, false);
	}

	default EmcTransaction removeEmc(ItemStack itemStack, long emc, boolean ignoreLimit) {
		return addEmc(itemStack, -emc, ignoreLimit);
	}

}
