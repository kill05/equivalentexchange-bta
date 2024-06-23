package com.github.kill05.equivalentexchange.tile.emc;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.ITileEmcHolder;
import com.github.kill05.equivalentexchange.tile.InventoryTileEntity;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.player.inventory.IInventory;

public abstract class InventoryEmcTileEntity<T extends InventoryEmcTileEntity<T>> extends InventoryTileEntity implements ITileEmcHolder<T> {

	protected long emc;

	public InventoryEmcTileEntity(IInventory inventory) {
		super(inventory);
	}

	public InventoryEmcTileEntity(String name, int slots) {
		super(name, slots);
	}


	@Override
	public void tick() {
		tickEmc();
	}


	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		readEmc(tag);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		writeEmc(tag);
	}

	@Override
	public long getEmc() {
		return emc;
	}

	@Override
	public EmcTransaction setEmc(long emc, boolean ignoreLimit) {
		return EmcTransaction.transactionSetEmc(emc, getMaxEmc(), ignoreLimit, aLong -> this.emc = aLong);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T asTile() {
		return (T) this;
	}
}
