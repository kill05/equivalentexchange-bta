package com.github.kill05.equivalentexchange.blocks;

import com.github.kill05.equivalentexchange.EEGuis;
import com.github.kill05.equivalentexchange.tile.EnergyCondenserTile;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class EnergyCondenserBlock extends AlchemicalChestBlock {

	public EnergyCondenserBlock(String key, int id) {
		super(key, id);
	}

	@Override
	public boolean onBlockRightClicked(World world, int x, int y, int z, EntityPlayer player, Side side, double xHit, double yHit) {
		if(player.isSneaking()) return false;
		EEGuis.ENERGY_CONDENSER.open(player, x, y, z);
		return true;
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new EnergyCondenserTile();
	}

}
