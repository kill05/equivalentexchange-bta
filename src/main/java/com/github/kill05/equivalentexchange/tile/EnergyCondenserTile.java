package com.github.kill05.equivalentexchange.tile;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.IEmcHolder;
import com.github.kill05.equivalentexchange.utils.InventoryUtils;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.IInventory;

public class EnergyCondenserTile extends AlchemicalChestTile implements IEmcHolder {

	public static final int BURN_SPEED = 1;
	private long emc;
	private EmcKey output = new EmcKey(Item.diamond.id, 0);

	public EnergyCondenserTile() {
		super(13 * 7);
	}

	@Override
	public void tick() {
		if (output == null) return;
		IInventory inv = getInputInventory();

		// Burn first available item
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack itemStack = inv.getStackInSlot(i);
			if (itemStack == null || output.matches(itemStack)) continue;

			ItemStack burnStack = itemStack.splitStack(BURN_SPEED);
			if (!burnItem(burnStack).isSuccessful()) continue;

			itemStack.stackSize -= BURN_SPEED;
			if (itemStack.stackSize <= 0) inv.setInventorySlotContents(i, null);
			break;
		}

		// Produce item if there's enough emc
		Long value = output.emcValue();
		if (value == null || value > emc) return;

		if (removeEmc(value).success())
			InventoryUtils.addItem(inv, output.itemStack());
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
		this.output = output;
	}


	public IInventory getInputInventory() {
		return this;
	}
}
