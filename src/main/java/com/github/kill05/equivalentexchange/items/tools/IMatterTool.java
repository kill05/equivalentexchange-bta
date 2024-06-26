package com.github.kill05.equivalentexchange.items.tools;

import com.github.kill05.equivalentexchange.items.IAbilityItem;
import com.github.kill05.equivalentexchange.items.IChargeableItem;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.data.tag.Tag;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.Nullable;

public interface IMatterTool extends IChargeableItem, IAbilityItem {

	MatterToolMaterial getMatterMaterial();

	@Override
	default int getMaxCharge(ItemStack itemStack) {
		return getMatterMaterial().getMaxCharge(this);
	}

	default void breakBlock(World world, EntityLiving entity, int x, int y, int z, @Nullable Tag<Block> requiredTag) {
		Block block = world.getBlock(x, y, z);
		if(block == null || (requiredTag != null && !block.hasTag(requiredTag))) return;

		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		ItemStack[] drops = block.getBreakResult(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, tile);

		world.setBlockWithNotify(x, y, z, 0);

		if(drops == null || (entity instanceof EntityPlayer player && !player.getGamemode().consumeBlocks())) return;
		for (ItemStack stack : drops) {
			world.dropItem(x, y, z, stack);
		}
	}

	default float getDamageModifier(ItemStack itemStack) {
		return 1f + (float) getCharge(itemStack) / getMaxCharge(itemStack);
	}

}
