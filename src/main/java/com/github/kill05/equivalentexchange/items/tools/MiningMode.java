package com.github.kill05.equivalentexchange.items.tools;

public enum MiningMode {
	NONE(0,  0,  0, 0, 0, 0),
	TALL(1,  1,  0, 0, 0, 0),
	WIDE(0,  0,  1, 1, 0, 0),
	LONG(0,  0,  0, 0, 0, 2);

	private final int down;
	private final int up;
	private final int left;
	private final int right;
	private final int backwards;
	private final int forwards;

	MiningMode(int down, int up, int left, int right, int backwards, int forwards) {
		this.left = left;
		this.down = down;
		this.backwards = backwards;
		this.right = right;
		this.up = up;
		this.forwards = forwards;
	}

	public int getDown() {
		return down;
	}

	public int getUp() {
		return up;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getBackwards() {
		return backwards;
	}

	public int getForwards() {
		return forwards;
	}
}
