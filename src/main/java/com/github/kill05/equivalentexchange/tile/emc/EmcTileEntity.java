package com.github.kill05.equivalentexchange.tile.emc;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.github.kill05.equivalentexchange.emc.holder.ITileEmcHolder;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.net.packet.Packet;
import net.minecraft.core.net.packet.Packet140TileEntityData;

public abstract class EmcTileEntity<T extends EmcTileEntity<T>> extends TileEntity implements ITileEmcHolder<T> {

	protected long emc;

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
	public Packet getDescriptionPacket() {
		return new Packet140TileEntityData(this);
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
