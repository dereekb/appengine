package com.dereekb.gae.utilities.data;


public class StringUtility {

	public static int lengthOfStrings(Iterable<String> strings) {
		int length = 0;

		for (String string : strings) {
			length += string.length();
		}

		return length;
	}

}
