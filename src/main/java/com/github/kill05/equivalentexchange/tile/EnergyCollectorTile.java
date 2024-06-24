package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.EEBlocks;
import com.github.kill05.equivalentexchange.EEItems;
import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.blocks.EnergyCollectorBlock;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.mixins.accessors.WorldAccessor;
import com.github.kill05.equivalentexchange.tile.emc.InventoryEmcTileEntity;
import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.InventoryBasic;
import sunsetsatellite.catalyst.core.util.Connection;
import sunsetsatellite.catalyst.core.util.Direction;
import sunsetsatellite.catalyst.core.util.IItemIO;

import java.util.HashMap;
import java.util.Map;

public class EnergyCollectorTile extends InventoryEmcTileEntity<EnergyCollectorTile> implements IItemIO {

	public static final Map<EmcKey, EmcKey> CONVERSION_MAP = new HashMap<>();

	private int tier;
	private EmcKey filter;

	public EnergyCollectorTile(int tier) {
		super(new InventoryBasic(null, 15));
		this.tier = tier;
	}

	public EnergyCollectorTile() {
		this(0);
	}


	@Override
	public void tick() {
		long genPerSec = getGeneration();
		long gcd = NumberUtils.gcd(genPerSec, 20);
		long genPerTick = genPerSec / gcd;

		// Generate EMC
		if (((WorldAccessor) worldObj).getRuntime() % (20 / gcd) == 0) {
			addEmc(genPerTick);
		}

		ItemStack itemStack = getStackInSlot(0);
		if (itemStack != null) {
			EmcKey input = new EmcKey(itemStack);
			EmcKey conversion = CONVERSION_MAP.get(input);
			boolean isHolder = itemStack.getItem() instanceof IItemEmcHolder;

			// Move item to output slot
			if (
				!isHolder && (conversion == null || input.equals(getFilter())) ||
					isHolder && ((IItemEmcHolder) itemStack.getItem()).isFull(itemStack)
			) {
				setInventorySlotContents(0, InventoryUtils.addItem(this, itemStack, 14));
			}

			// Convert input slot
			convertInput(itemStack, conversion);
		}

		super.tick();
	}

	protected void convertInput(ItemStack inputStack, EmcKey conversion) {
		EmcKey input = new EmcKey(inputStack);
		if (conversion == null) {
			if (!(inputStack.getItem() instanceof IItemEmcHolder holder)) return;
			EmcTransaction transaction = holder.addEmc(inputStack, getEmc());
			removeEmc(transaction.transferredAmount());
			return;
		}

		long cost = Math.max(conversion.emcValue() - input.emcValue(), 0);
		if (getEmc() < cost) return;

		ItemStack itemStack = InventoryUtils.addItem(this, conversion.itemStack(), 13);
		if (itemStack == null) {
			InventoryUtils.removeItem(this, 1, 0);
			removeEmc(cost);
		}
	}

	@Override
	public boolean shouldPullEmc() {
		return getStackInSlot(0) != null;
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		tier = tag.getIntegerOrDefault("tier", -1);
		if (!isTierValid())
			EquivalentExchange.LOGGER.warn(String.format("Loaded tile entity with invalid tier '%s'.", tier));

		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		tag.putInt("tier", tier);
		super.writeToNBT(tag);
	}


	@Override
	public int getActiveItemSlotForSide(Direction direction) {
		return 0;
	}

	@Override
	public int getActiveItemSlotForSide(Direction direction, ItemStack itemStack) {
		return 0;
	}

	@Override
	public Connection getItemIOForSide(Direction direction) {
		return null;
	}


	public EnergyCollectorBlock getCollector() {
		return EnergyCollectorBlock.TIER_MAP.get(tier);
	}

	public int getTier() {
		return tier;
	}

	public boolean isTierValid() {
		return EnergyCollectorBlock.TIER_MAP.containsKey(getTier());
	}

	public long getGeneration() {
		return getCollector().getGeneration();
	}

	@Override
	public long getMaxEmc() {
		return getCollector().getMaxEmc();
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
