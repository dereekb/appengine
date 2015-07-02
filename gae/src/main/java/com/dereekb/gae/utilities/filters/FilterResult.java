package com.dereekb.gae.utilities.filters;


public enum FilterResult {

	/**
	 * Object failed.
	 */
	PASS,
	
	/**
	 * Object passed.
	 */
	FAIL;

	public static FilterResult withBoolean(boolean bool) {
	    FilterResult result = (bool) ? PASS : FAIL;
	    return result;
    }
}
