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
		if (bool == null) {
			return false;
		} else {
			return bool;
		}
	}

}
