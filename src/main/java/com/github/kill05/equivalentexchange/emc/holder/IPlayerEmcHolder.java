package com.github.kill05.equivalentexchange.emc.holder;

import com.github.kill05.equivalentexchange.emc.EmcKey;

import java.util.List;

public interface IPlayerEmcHolder extends IEmcHolder {

	List<EmcKey> getKnownItems();

	void learnItems(List<EmcKey> items);

	boolean knowsItem(EmcKey key);

	boolean learnItem(EmcKey key);

	boolean unlearnItem(EmcKey key);

	boolean hasTome();

	void setTome(boolean tome);


	@Override
	default long getMaxEmc() {
		return Long.MAX_VALUE;
	}
}
