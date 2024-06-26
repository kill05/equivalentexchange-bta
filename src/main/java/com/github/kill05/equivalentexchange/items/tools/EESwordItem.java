package com.github.kill05.equivalentexchange.items.tools;

import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.List;

public class EESwordItem extends ItemToolSword implements IMatterTool {

	private final MatterToolMaterial material;

	public EESwordItem(String name, int id, MatterToolMaterial material) {
		super(name, id, material);
		this.material = material;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
		onAbilityUse(player, itemStack);
		return itemStack;
	}

	@Override
	public void onAbilityUse(EntityPlayer player, ItemStack itemStack) {
		int charge = getCharge(itemStack);
		if(player.isSwinging || charge <= 0) return;

		float expand = 2f + charge * 1.5f;
		List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player,
			new AABB(player.x, player.y, player.z, player.x, player.y, player.z).expand(expand, expand, expand)
		);

		for (Entity entity : entities) {
			player.attackTargetEntityWithCurrentItem(entity);
		}

		IMatterTool.super.onAbilityUse(player, itemStack);
	}

	@Override
	public MatterToolMaterial getMatterMaterial() {
		return material;
	}


}
