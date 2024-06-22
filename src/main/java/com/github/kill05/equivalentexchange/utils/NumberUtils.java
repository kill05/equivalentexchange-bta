package com.github.kill05.equivalentexchange.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class NumberUtils {

	private static final DecimalFormat NUMBER_FORMAT;
	private static final DecimalFormat LETTER_FORMAT = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(Locale.US));
	private static final String[] numberLiterals = {"", "k", "M", "B", "t", "q", "Q"};

	public static String letterFormat(double value) {
		int index = 0;
		while ((value / 1000) >= 1) {
			value = value / 1000;
			index++;
		}
		return LETTER_FORMAT.format(value) + numberLiterals[index];
	}

	public static String formatNumber(double number) {
		return NUMBER_FORMAT.format(number);
	}


	static {
		DecimalFormatSymbols decimalFormat = new DecimalFormatSymbols(Locale.ENGLISH);
		decimalFormat.setGroupingSeparator('.');
		NUMBER_FORMAT = new DecimalFormat("#,###", decimalFormat);
	}
}
