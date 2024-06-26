package com.github.kill05.equivalentexchange.items.tools;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.data.registry.Registries;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.item.tool.ItemToolAxe;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.List;

public class EEAxeItem extends ItemToolAxe implements IMatterTool {

	public EEAxeItem(String name, int id, MatterToolMaterial material) {
		super(name, id, material);
	}

	@Override
	public boolean onUseItemOnBlock(ItemStack itemStack, EntityPlayer player, World world, int blockX, int blockY, int blockZ, Side side, double xPlaced, double yPlaced) {
		if (world.isClientSide || player.isSwinging || getCharge(itemStack) <= 0) return false;

		if(harvestTree(player, world, blockX, blockY, blockZ)) {
			IMatterTool.super.onAbilityUse(player, itemStack);
		}

		return true;
	}

	private boolean harvestTree(EntityPlayer player, World world, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		List<ItemStack> logGroup = Registries.ITEM_GROUPS.getItem("minecraft:logs");

		if (!isLog(block, logGroup)) return false;
		breakBlock(world, player, x, y, z, BlockTags.MINEABLE_BY_AXE);

		for (Direction direction : Direction.directions) {
			harvestTree(player, world, x + direction.getOffsetX(), y + direction.getOffsetY(), z + direction.getOffsetZ());
		}

		return true;
	}

	private boolean isLog(Block block, List<ItemStack> group) {
		if (block == null) return false;
		for (ItemStack groupItem : group) {
			if (groupItem == null || !(groupItem.getItem() instanceof ItemBlock itemBlock)) continue;
			if (itemBlock.getBlock().id == block.id) return true;
		}

		return false;
	}

	@Override
	public void onAbilityUse(EntityPlayer player, ItemStack itemStack) {

	}

	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return 1;
	}

	@Override
	public MatterToolMaterial getMatterMaterial() {
		return (MatterToolMaterial) getMaterial();
	}
}
