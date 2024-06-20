package com.github.kill05.projectbta;

import com.github.kill05.projectbta.block.transtable.BurnResult;
import com.github.kill05.projectbta.registry.EmcKey;
import net.minecraft.core.item.ItemStack;

public interface IEmcHolder {

	long getEmc();

	void setEmc(long emc);

	default void addEmc(long emc) {
		setEmc(getEmc() + emc);
	}

	default void removeEmc(long emc) {
		addEmc(-emc);
	}

	default BurnResult burnItem(ItemStack itemStack) {
		EmcKey key = new EmcKey(itemStack);
		Long emc = key.emcValue();
		if(emc == null) return BurnResult.FAILURE_NO_VALUE;

		addEmc(emc * itemStack.stackSize);

		boolean learned = false;
		if(this instanceof ProjectPlayer player) learned = player.learnItem(key);
		return learned ? BurnResult.SUCCESS_LEARNED : BurnResult.SUCCESS;
	}

}
