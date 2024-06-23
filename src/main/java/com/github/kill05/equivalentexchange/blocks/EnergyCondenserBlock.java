package com.github.kill05.equivalentexchange.blocks;

import com.github.kill05.equivalentexchange.inventory.gui.EEGuis;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

public class EnergyCondenserBlock extends BlockTileEntityRotatable {

	public EnergyCondenserBlock(String key, int id) {
		super(key, id, Material.stone);
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if(player.isSneaking()) return false;
		EEGuis.ENERGY_CONDENSER.open(player, x, y, z);
		return true;
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new EnergyCondenserTile();
	}
}