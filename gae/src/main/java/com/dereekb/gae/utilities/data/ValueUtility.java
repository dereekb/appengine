package com.dereekb.gae.utilities.data;

/**
 * Object/Value utility functions.
 *
 * @author dereekb
 *
 */
public class ValueUtility {

	public static <T> T defaultTo(T value,
	                              T defaultValue) {
		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	public static boolean valueOf(Boolean bool) {
		return valueOf(bool, false);
	}

	public static boolean valueOf(Boolean bool,
	                              boolean defaultValue) {
		return defaultTo(bool, defaultValue);
	}

}
