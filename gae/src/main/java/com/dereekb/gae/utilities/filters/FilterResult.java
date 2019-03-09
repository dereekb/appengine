package com.dereekb.gae.utilities.filters;

/**
 * FilterResult enumerations.
 * 
 * @author dereekb
 *
 */
public enum FilterResult {

	/**
	 * Object failed.
	 */
	PASS(true),

	/**
	 * Object passed.
	 */
	FAIL(false);

	public final boolean bool;

	private FilterResult(boolean bool) {
		this.bool = bool;
	}

	public FilterResult and(boolean bool) {
		return FilterResult.valueOf(this.bool && bool);
	}

	public FilterResult and(FilterResult result) {
		return FilterResult.valueOf(this.bool && result.bool);
	}

	public static FilterResult valueOf(boolean bool) {
		return (bool) ? PASS : FAIL;
	}

	public static FilterResult withBoolean(boolean bool) {
		return (bool) ? PASS : FAIL;
	}

}
