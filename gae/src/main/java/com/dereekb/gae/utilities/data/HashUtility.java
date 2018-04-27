package com.dereekb.gae.utilities.data;

public class HashUtility {

	public static final Integer simpleHash(String value) {
		int hash = 7;

		for (int i = 0; i < value.length(); i++) {
			hash = hash * 31 + value.charAt(i);
		}

		return hash;
	}

}
