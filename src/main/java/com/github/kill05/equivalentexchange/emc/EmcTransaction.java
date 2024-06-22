package com.github.kill05.equivalentexchange.emc;

import java.util.function.Consumer;

/**
 * Represents the result of an EmcHolder transaction
 *
 * @param success whether the transaction was successful
 * @param originalAmount the original transfer amount
 * @param transferredAmount the actual transferred amount due to max capacity or other limits
 */
public record EmcTransaction(boolean success, long originalAmount, long transferredAmount) {

	public EmcTransaction(boolean success, long originalAmount, long transferredAmount) {
		this.success = success;
		this.originalAmount = originalAmount;
		this.transferredAmount = transferredAmount;
	}

	public static EmcTransaction transactionSetEmc(long amount, long maxEmc, boolean ignoreLimit, Consumer<Long> emcSetter) {
		if(amount < 0) {
			return new EmcTransaction(false, amount, 0);
		}

		long transferAmount = ignoreLimit ? amount : Math.min(maxEmc, amount);
		emcSetter.accept(transferAmount);
		return new EmcTransaction(true, amount, transferAmount);
	}

	public static EmcTransaction transactionModifyEmc(long originalAmount, long modifyAmount, long maxEmc, boolean ignoreLimit, Consumer<Long> emcSetter) {
		long transferAmount = ignoreLimit ? modifyAmount : Math.min(maxEmc, originalAmount + modifyAmount) - originalAmount;
		emcSetter.accept(originalAmount + transferAmount);
		return new EmcTransaction(true, originalAmount, transferAmount);
	}



	/**
	 * @return the amount not transferred in the transaction due to max capacity or other limits
	 */
	public long remainingAmount() {
		return originalAmount - transferredAmount;
	}

}
