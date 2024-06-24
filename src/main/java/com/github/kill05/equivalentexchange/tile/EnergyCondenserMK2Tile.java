package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IEmcHolder;
import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public class EnergyCondenserMK2Tile extends EnergyCondenserTile implements IEmcHolder {

	public static final int BURN_SPEED = 1;
	private long emc;
	private EmcKey output;

	public EnergyCondenserMK2Tile() {
		super(6 * 7);
	}

	@Override
	public void tick() {
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
	public void readFromNBT(CompoundTag compound) {
		super.readFromNBT(compound);
		emc = compound.getLong("emc");
		output = EmcKey.deserialize(compound.getCompound("output"));
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		tag.putLong("emc", emc);
		tag.putCompound("output", output != null ? output.serialize() : null);
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
}
