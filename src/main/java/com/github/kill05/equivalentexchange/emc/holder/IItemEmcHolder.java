package com.github.kill05.equivalentexchange.emc.holder;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.items.ICustomDurabilityBar;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import net.minecraft.core.item.ItemStack;

public interface IItemEmcHolder extends ICustomDurabilityBar {

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

	default boolean isFull(ItemStack itemStack) {
		return getEmc(itemStack) >= getMaxEmc(itemStack);
	}

	default String getFormattedEmc(ItemStack itemStack) {
		return NumberUtils.formatNumber(getEmc(itemStack));
	}


	@Override
	default double getMaxDisplayDurability(ItemStack itemStack) {
		return getMaxEmc(itemStack);
	}

	@Override
	default double getDisplayDurability(ItemStack itemStack) {
		return getEmc(itemStack);
	}
}
