package com.github.kill05.projectbta.block.transtable;

public enum BurnResult {

	SUCCESS_LEARNED(true),
	SUCCESS(true),
	FAILURE_NO_VALUE(false);

	private final boolean success;

	BurnResult(boolean success) {
		this.success = success;
	}

	public boolean isSuccessful() {
		return success;
	}
}
