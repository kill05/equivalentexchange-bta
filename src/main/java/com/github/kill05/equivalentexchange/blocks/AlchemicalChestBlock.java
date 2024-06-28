package com.github.kill05.equivalentexchange.blocks;

import com.github.kill05.equivalentexchange.EEGuis;
import com.github.kill05.equivalentexchange.tile.AlchemicalChestTile;
import com.github.kill05.equivalentexchange.tile.InventoryTileEntity;
import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class AlchemicalChestBlock extends BlockTileEntityRotatable {

	public AlchemicalChestBlock(String name, int id) {
		super(name, id, Material.stone);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		if(player.isSneaking()) return false;
		EEGuis.ALCHEMICAL_CHEST.open(player, x, y, z);
		return true;
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new AlchemicalChestTile();
	}

	@Override
	public void onBlockRemoved(World world, int x, int y, int z, int data) {
		if(world.getBlockTileEntity(x, y, z) instanceof InventoryTileEntity tile) {
			InventoryUtils.dropInventoryContents(tile, world, x, y, z);
		}

		super.onBlockRemoved(world, x, y, z, data);
	}
}
