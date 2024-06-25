package com.github.kill05.equivalentexchange.items;

import com.github.kill05.equivalentexchange.EquivalentExchange;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class SuperSecretItem extends Item {

	public SuperSecretItem(int id) {
		super("super_secret_item", id);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer player) {
		world.playSoundAtEntity(null, player, EquivalentExchange.MOD_ID + ".super_secret_sound", 2.0f, 1.0f);
		return itemstack;
	}
}
