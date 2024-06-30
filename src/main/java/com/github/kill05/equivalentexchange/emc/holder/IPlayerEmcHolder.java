package com.github.kill05.equivalentexchange.emc.holder;

import com.github.kill05.equivalentexchange.emc.EmcKey;
import net.minecraft.core.entity.player.EntityPlayer;

import java.util.List;

public interface IPlayerEmcHolder extends IEmcHolder {

	List<EmcKey> getKnownItems();

	List<EmcKey> getActualKnownItems();

	void learnItems(List<EmcKey> items);

	boolean knowsItem(EmcKey key);

	boolean learnItem(EmcKey key);

	boolean unlearnItem(EmcKey key);

	boolean hasTome();

	void setTome(boolean tome);

	default void transferDataToNewPlayer(EntityPlayer newPlayer) {
		IPlayerEmcHolder newHolder = (IPlayerEmcHolder) newPlayer;
		newHolder.setEmc(getEmc());
		newHolder.learnItems(getActualKnownItems());
		newHolder.setTome(hasTome());
	}

	@Override
	default long getMaxEmc() {
		return Long.MAX_VALUE;
	}
}
