package com.github.kill05.equivalentexchange.utils;

import org.lwjgl.input.Keyboard;

public final class KeyUtils {


	public static boolean isShiftPressed() {
		return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
	}

	public static boolean isCtrlPressed() {
		return Keyboard.isKeyDown(Keyboard.KEY_LCONTROL) || Keyboard.isKeyDown(Keyboard.KEY_RCONTROL);
	}

}
