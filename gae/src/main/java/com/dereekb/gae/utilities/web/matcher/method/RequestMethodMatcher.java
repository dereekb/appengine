package com.dereekb.gae.utilities.web.matcher.method;

/**
 * Request Method (GET, PUT, etc.) matcher.
 * <p>
 * Matches should be case-insensitive.
 * 
 * @author dereekb
 *
 */
public interface RequestMethodMatcher {

	/**
	 * Checks whether or not the method matches.
	 * 
	 * @param method
	 *            {@link String}. Never {@code null}.
	 * @return {@code true} if the method matches.
	 */
	boolean matches(String method);

}
