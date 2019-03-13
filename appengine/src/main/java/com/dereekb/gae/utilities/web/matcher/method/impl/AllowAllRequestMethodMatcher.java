package com.dereekb.gae.utilities.web.matcher.method.impl;

import com.dereekb.gae.utilities.web.matcher.method.RequestMethodMatcher;

/**
 * {@link RequestMethodMatcher} implementation that always matches {@code true}.
 * 
 * @author dereekb
 *
 */
public class AllowAllRequestMethodMatcher
        implements RequestMethodMatcher {

	public static final RequestMethodMatcher SINGLETON = new AllowAllRequestMethodMatcher();

	// MARK: RequestMethodMatcher
	@Override
	public boolean matches(String method) {
		return true;
	}

}
