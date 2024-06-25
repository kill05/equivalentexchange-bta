package com.github.kill05.equivalentexchange.items.tools;

import net.minecraft.core.HitResult;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolPickaxe;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

public class EEPickaxeItem extends ItemToolPickaxe implements IMatterPickaxe {

	public EEPickaxeItem(String name, int id, MatterToolMaterial material) {
		super(name, id, material);
	}


	@Override
	public float getStrVsBlock(ItemStack itemStack, Block block) {
		return super.getStrVsBlock(itemStack, block) * (1f + (float) getCharge(itemStack) / getMaxCharge(itemStack));
	}

	@Override
	public boolean onBlockDestroyed(World world, ItemStack itemStack, int i, int x, int y, int z, EntityLiving entity) {
		if(world.isClientSide) return true;

		HitResult hitResult = entity.rayTrace(10.0, 0.05f);
		if(hitResult == null || hitResult.side == null) return true;

		AABB box = getMiningBox(itemStack, entity, hitResult.side, x, y, z);

		for(int breakX = (int) box.minX; breakX < (int) box.maxX; breakX++) {
			for(int breakY = (int) box.minY; breakY < (int) box.maxY; breakY++) {
				for(int breakZ = (int) box.minZ; breakZ < (int) box.maxZ; breakZ++) {
					if(breakX == 0 && breakY == 0 && breakZ == 0) continue;
					breakBlock(world, entity, breakX, breakY, breakZ);
				}
			}
		}

		return true;
	}

	private void breakBlock(World world, EntityLiving entity, int x, int y, int z) {
		Block block = world.getBlock(x, y, z);
		if(block == null) return;

		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		ItemStack[] drops = block.getBreakResult(world, EnumDropCause.PROPER_TOOL, x, y, z, meta, tile);

		world.setBlockWithNotify(x, y, z, 0);

		if(drops == null || (entity instanceof EntityPlayer player && !player.getGamemode().consumeBlocks())) return;
		for (ItemStack stack : drops) {
			world.dropItem(x, y, z, stack);
		}
	}


	@Override
	public int getMaxCharge(ItemStack itemStack) {
		return getMaterial().getMaxCharge();
	}


	@Override
	public MatterToolMaterial getMaterial() {
		return (MatterToolMaterial) super.getMaterial();
	}
}
