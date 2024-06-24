package com.github.kill05.equivalentexchange.emc.holder;

import com.github.kill05.equivalentexchange.emc.EmcTransaction;
import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.util.helper.Direction;

public interface ITileEmcHolder<T extends TileEntity & ITileEmcHolder<T>> extends IEmcHolder {

	T asTile();

	default boolean shouldPullEmc() {
		return false;
	}

	default void tickEmc() {
		if(!shouldPullEmc()) return;

		T thisTile = asTile();

		for (Direction dir : Direction.values()) {
			TileEntity tile = thisTile.worldObj.getBlockTileEntity(
				thisTile.x + dir.getOffsetX(),
				thisTile.y + dir.getOffsetY(),
				thisTile.z + dir.getOffsetZ()
			);

			if(!(tile instanceof ITileEmcHolder<?> emcTile) || emcTile.shouldPullEmc()) continue;
			EmcTransaction transaction = emcTile.removeEmc(emcTile.getEmc());
			if(transaction.success()) addEmc(-transaction.transferredAmount());
		}
	}

	default void readEmc(CompoundTag tag) {
		setEmc(tag.getLong("emc"));
	}

	default void writeEmc(CompoundTag tag) {
		tag.putLong("emc", getEmc());
	}
}
