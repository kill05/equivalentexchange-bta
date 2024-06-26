package com.github.kill05.equivalentexchange.items;

import net.minecraft.core.item.IItemConvertible;
import net.minecraft.core.item.ItemStack;

public interface ICustomDurabilityBar extends IItemConvertible {

	double getMaxDisplayDurability(ItemStack itemStack);

	double getDisplayDurability(ItemStack itemStack);

}
