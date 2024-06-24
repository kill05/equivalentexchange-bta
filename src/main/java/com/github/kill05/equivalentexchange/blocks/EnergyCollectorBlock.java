package com.github.kill05.equivalentexchange.blocks;

import com.github.kill05.equivalentexchange.EEGuis;
import com.github.kill05.equivalentexchange.tile.EnergyCollectorTile;
import net.minecraft.core.block.BlockTileEntityRotatable;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;

import java.util.HashMap;
import java.util.Map;

public class EnergyCollectorBlock extends BlockTileEntityRotatable {

	public static final Map<Integer, EnergyCollectorBlock> TIER_MAP = new HashMap<>();

	private final int tier;
	private final long generation;
	private final long maxEmc;

	public EnergyCollectorBlock(int tier, long maxEmc, long generation, int id) {
		super("collector_mk" + tier, id, Material.stone);
		this.tier = tier;
		this.maxEmc = maxEmc;
		this.generation = generation;

		TIER_MAP.put(tier, this);
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new EnergyCollectorTile(tier);
	}

	@Override
	public boolean blockActivated(World world, int x, int y, int z, EntityPlayer player) {
		if(player.isSneaking()) return false;
		EEGuis.ENERGY_COLLECTOR.open(player, x, y, z);
		return true;
	}

	public int getTier() {
		return tier;
	}

	public long getMaxEmc() {
		return maxEmc;
	}

	public long getGeneration() {
		return generation;
	}
}
