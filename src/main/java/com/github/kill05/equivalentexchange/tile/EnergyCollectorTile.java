package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.EEBlocks;
import com.github.kill05.equivalentexchange.EEItems;
import com.github.kill05.equivalentexchange.blocks.EnergyCollectorBlock;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.mixins.accessors.WorldAccessor;
import com.github.kill05.equivalentexchange.tile.emc.InventoryEmcTileEntity;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.player.inventory.InventoryBasic;

import java.util.HashMap;
import java.util.Map;

public class EnergyCollectorTile extends InventoryEmcTileEntity<EnergyCollectorTile> {

	public static final Map<EmcKey, EmcKey> CONVERSION_MAP = new HashMap<>();

	private EmcKey filter;

	public EnergyCollectorTile() {
		super(new InventoryBasic(null, 15));
	}


	@Override
	public void tick() {
		super.tickEmc();
		if(((WorldAccessor) worldObj).getRuntime() % 20 != 0) return;

		addEmc(getGeneration());
	}


	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
	}


	@Override
	public EnergyCollectorBlock getBlockType() {
		return (EnergyCollectorBlock) super.getBlockType();
	}


	public int getTier() {
		return getBlockType().getTier();
	}

	public long getGeneration() {
		return getBlockType().getGeneration();
	}

	@Override
	public long getMaxEmc() {
		return getBlockType().getMaxEmc();
	}

	public EmcKey getFilter() {
		return filter;
	}

	public void setFilter(EmcKey filter) {
		this.filter = filter;
	}


	static {
		CONVERSION_MAP.put(new EmcKey(Item.coal, 1), new EmcKey(Item.dustRedstone));
		CONVERSION_MAP.put(new EmcKey(Item.dustRedstone), new EmcKey(Item.coal));
		CONVERSION_MAP.put(new EmcKey(Item.coal), new EmcKey(Item.sulphur));
		CONVERSION_MAP.put(new EmcKey(Item.sulphur), new EmcKey(Item.dustGlowstone));
		CONVERSION_MAP.put(new EmcKey(Item.dustGlowstone), new EmcKey(EEItems.ALCHEMICAL_COAL));
		CONVERSION_MAP.put(new EmcKey(EEItems.ALCHEMICAL_COAL), new EmcKey(Block.blockRedstone));
		CONVERSION_MAP.put(new EmcKey(Block.blockRedstone), new EmcKey(Block.blockCoal));
		CONVERSION_MAP.put(new EmcKey(Block.blockCoal), new EmcKey(Block.glowstone));
		CONVERSION_MAP.put(new EmcKey(Block.glowstone), new EmcKey(EEItems.MOBIUS_FUEL));
		CONVERSION_MAP.put(new EmcKey(EEItems.MOBIUS_FUEL), new EmcKey(EEBlocks.ALCHEMICAL_COAL_BLOCK));
		CONVERSION_MAP.put(new EmcKey(EEBlocks.ALCHEMICAL_COAL_BLOCK), new EmcKey(EEItems.AETERNALIS_FUEL));
		CONVERSION_MAP.put(new EmcKey(EEItems.AETERNALIS_FUEL), new EmcKey(EEBlocks.MOBIUS_FUEL_BLOCK));
		CONVERSION_MAP.put(new EmcKey(EEBlocks.MOBIUS_FUEL_BLOCK), new EmcKey(EEBlocks.AETERNALIS_FUEL_BLOCK));
	}

}
