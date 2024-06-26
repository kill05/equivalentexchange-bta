package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.ITileEmcHolder;
import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet140TileEntityData;
import net.minecraft.core.player.inventory.IInventory;

public class EnergyCondenserTile extends AlchemicalChestTile implements ITileEmcHolder<EnergyCondenserTile> {

	public static final int BURN_SPEED = 1;
	private long emc;
	private EmcKey output;

	protected EnergyCondenserTile(int slots) {
		super(slots);
	}

	public EnergyCondenserTile() {
		this(13 * 7);
	}


	@Override
	public void tick() {
		tickEmc();

		if (output == null) return;
		IInventory inv = getInputInventory();

		// Burn first available item
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack itemStack = inv.getStackInSlot(i);
			if (itemStack == null || output.matches(itemStack)) continue;

			ItemStack burnStack = itemStack.copy();
			burnStack.stackSize = BURN_SPEED;
			if (!burnItem(burnStack).isSuccessful()) continue;

			itemStack.stackSize -= BURN_SPEED;
			if (itemStack.stackSize <= 0) inv.setInventorySlotContents(i, null);
			break;
		}

		// Produce item if there's enough emc
		Long value = output.emcValue();
		if (value == null || value > emc) return;

		if(InventoryUtils.addItem(inv, output.itemStack()) == null)
			removeEmc(value);
	}

	@Override
	public boolean shouldPullEmc() {
		return output != null;
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		readEmc(tag);
		output = EmcKey.deserialize(tag.getCompound("output"));
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		writeEmc(tag);
		if(output != null) tag.putCompound("output", output.serialize());
	}

	@Override
	public Packet getDescriptionPacket() {
		return new Packet140TileEntityData(this);
	}

	@Override
	public long getMaxEmc() {
		return Long.MAX_VALUE;
	}

	@Override
	public long getEmc() {
		return emc;
	}

	@Override
	public EmcTransaction setEmc(long emc, boolean ignoreLimit) {
		return EmcTransaction.transactionSetEmc(emc, getMaxEmc(), ignoreLimit, aLong -> this.emc = aLong);
	}


	public EmcKey getOutput() {
		return output;
	}

	public void setOutput(EmcKey output) {
		if(output != null && output.emcValue() == null) return;
		this.output = output;
	}


	public IInventory getInputInventory() {
		return this;
	}

	@Override
	public EnergyCondenserTile asTile() {
		return this;
	}
}
