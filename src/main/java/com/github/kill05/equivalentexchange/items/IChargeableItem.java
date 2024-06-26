package com.github.kill05.equivalentexchange.items;

import com.github.kill05.equivalentexchange.utils.NbtUtils;
import net.minecraft.core.item.ItemStack;

public interface IChargeableItem extends ICustomDurabilityBar {

	int getMaxCharge(ItemStack itemStack);

	default int getCharge(ItemStack itemStack) {
		int charge = NbtUtils.getMainCompound(itemStack.getData(), false).getInteger("charge");
		return Math.max(Math.min(charge, getMaxCharge(itemStack)), 0);
	}

	default boolean setCharge(ItemStack itemStack, int amount) {
		if(amount < 0 || amount > getMaxCharge(itemStack)) return false;
		NbtUtils.getMainCompound(itemStack.getData(), true).putInt("charge", amount);
		return true;
	}


	default boolean charge(ItemStack itemStack) {
		return setCharge(itemStack, getCharge(itemStack) + 1);
	}

	default boolean uncharge(ItemStack itemStack) {
		return setCharge(itemStack, getCharge(itemStack) - 1);
	}

	@Override
	default double getMaxDisplayDurability(ItemStack itemStack) {
		return getMaxCharge(itemStack);
	}

	@Override
	default double getDisplayDurability(ItemStack itemStack) {
		return getCharge(itemStack);
	}
}
