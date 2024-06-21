package com.github.kill05.projectbta.emc.holder;

import com.github.kill05.projectbta.ProjectBTA;
import com.github.kill05.projectbta.blocks.transtable.BurnResult;
import com.github.kill05.projectbta.emc.EmcKey;
import com.github.kill05.projectbta.emc.EmcTransaction;
import net.minecraft.core.item.ItemStack;

public interface IEmcHolder {

	long getMaxEmc();

	long getEmc();

	EmcTransaction setEmc(long emc, boolean ignoreLimit);

	default EmcTransaction setEmc(long emc) {
		return setEmc(emc, false);
	}

	default EmcTransaction addEmc(long emc) {
		return addEmc(emc, false);
	}

	default EmcTransaction addEmc(long emc, boolean ignoreLimit) {
		return EmcTransaction.transactionModifyEmc(getEmc(), emc, getMaxEmc(), ignoreLimit, aLong ->
			setEmc(aLong, true)
		);
	}

	default EmcTransaction removeEmc(long emc) {
		return removeEmc(emc, false);
	}

	default EmcTransaction removeEmc(long emc, boolean ignoreLimit) {
		return addEmc(-emc, ignoreLimit);
	}

	default BurnResult burnItem(ItemStack itemStack) {
		EmcKey key = new EmcKey(itemStack);
		Long emc = ProjectBTA.getEmcValue(itemStack);
		if(emc == null) return BurnResult.FAILURE_NO_VALUE;

		EmcTransaction transaction = addEmc(emc * itemStack.stackSize);
		if(!transaction.success()) return BurnResult.FAILURE_TRANSACTION;

		return (this instanceof ProjectPlayer player && player.learnItem(key))
			? BurnResult.SUCCESS_LEARNED
			: BurnResult.SUCCESS;
	}

}
