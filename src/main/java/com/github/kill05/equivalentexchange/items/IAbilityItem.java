package com.github.kill05.equivalentexchange.items;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;

public interface IAbilityItem {

	default void onAbilityUse(EntityPlayer player, ItemStack itemStack) {
		player.swingItem();
		player.world.playSoundAtEntity(null, player, EquivalentExchange.MOD_ID + ".charge", 0.7f, 1f);
	}

}
