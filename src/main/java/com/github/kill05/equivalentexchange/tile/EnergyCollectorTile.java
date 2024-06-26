package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.EEBlocks;
import com.github.kill05.equivalentexchange.EEItems;
import com.github.kill05.equivalentexchange.EquivalentExchange;
import com.github.kill05.equivalentexchange.blocks.EnergyCollectorBlock;
import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IItemEmcHolder;
import com.github.kill05.equivalentexchange.mixins.accessors.client.MinecraftAccessor;
import com.github.kill05.equivalentexchange.mixins.accessors.WorldAccessor;
import com.github.kill05.equivalentexchange.tile.emc.InventoryEmcTileEntity;
import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import com.github.kill05.equivalentexchange.utils.NumberUtils;
import com.mojang.nbt.CompoundTag;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet140TileEntityData;
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
		//todo: change when client world runtime ticking is fixed
		long runtime = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
			? ((MinecraftAccessor) Minecraft.getMinecraft(this)).getTicksRan()
			: ((WorldAccessor) worldObj).getRuntime();

		if (runtime % (20 / gcd) == 0) {
			addEmc((long) (genPerTick * ((float) getLightLevel() / 15)));
		}

		ItemStack itemStack = getStackInSlot(0);
		if (itemStack != null) {
			EmcKey input = new EmcKey(itemStack);
			EmcKey conversion = CONVERSION_MAP.get(input);
			boolean isHolder = itemStack.getItem() instanceof IItemEmcHolder;

			// Move item to output slot
			boolean cond1 = isHolder && ((IItemEmcHolder) itemStack.getItem()).isFull(itemStack);
			boolean cond2 = !isHolder && (conversion == null || input.equals(getFilter()));
			if (cond2 || cond1) {
				setInventorySlotContents(0, InventoryUtils.addItem(this, itemStack, 14));
			}

			// Convert input slot
			convertInput(itemStack, conversion);
		}

		// Cycle items to input
		for(int i = 1; i < 14; i++) {
			ItemStack currentStack = getStackInSlot(i);
			setInventorySlotContents(i, null);
			InventoryUtils.addItem(this, currentStack);
		}

		super.tick();
	}

	protected void convertInput(ItemStack inputStack, EmcKey conversion) {
		EmcKey input = new EmcKey(inputStack);
		if(input.equals(filter)) return;

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
		filter = EmcKey.deserialize(tag.getCompound("filter"));
		if (!isTierValid())
			EquivalentExchange.LOGGER.warn(String.format("Loaded tile entity with invalid tier '%s'.", tier));

		super.readFromNBT(tag);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		tag.putInt("tier", tier);
		if(filter != null) tag.putCompound("filter", filter.serialize());
		super.writeToNBT(tag);
	}

	@Override
	public Packet getDescriptionPacket() {
		return new Packet140TileEntityData(this);
	}

	public int getLightLevel() {
		return worldObj.getBlockLightValue(x, y + 1, z);
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

	@Override
	public void setItemIOForSide(Direction direction, Connection connection) {

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
