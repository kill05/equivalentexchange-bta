package com.github.kill05.equivalentexchange.utils;

import org.lwjgl.input.Keyboard;

public final class KeyUtils {

	public static boolean isShiftPressed() {
		return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
	}

	public static boolean isCtrlPressed() {
		return Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);

	}

}
