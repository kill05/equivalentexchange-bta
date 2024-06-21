package com.github.kill05.projectbta.emc;

import com.github.kill05.projectbta.blocks.transtable.BurnResult;
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

		return (this instanceof ProjectPlayer player && player.learnItem(key))
			? BurnResult.SUCCESS_LEARNED
			: BurnResult.SUCCESS;
	}

}
