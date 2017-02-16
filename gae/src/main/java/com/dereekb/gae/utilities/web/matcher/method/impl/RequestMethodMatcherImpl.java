package com.dereekb.gae.utilities.web.matcher.method.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.utilities.collections.SingleItem;
import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;
import com.dereekb.gae.utilities.web.matcher.method.RequestMethodMatcher;

/**
 * {@link RequestMethodMatcher} implementation.
 * 
 * @author dereekb
 *
 */
public class RequestMethodMatcherImpl
        implements RequestMethodMatcher {

	private Set<String> allowedMethods;

	public RequestMethodMatcherImpl(String allowedMethod) throws IllegalArgumentException {
		this(new SingleItem<String>(allowedMethod));
	}

	public RequestMethodMatcherImpl(Collection<String> allowedMethods) throws IllegalArgumentException {
		this.setAllowedMethods(allowedMethods);
	}

	public Collection<String> getAllowedMethods() {
		return this.allowedMethods;
	}

	public void setAllowedMethods(Collection<String> allowedMethods) throws IllegalArgumentException {
		if (allowedMethods == null || allowedMethods.isEmpty()) {
			throw new IllegalArgumentException("AllowedMethods cannot be null or empty.");
		} else {
			this.allowedMethods = new CaseInsensitiveSet(allowedMethods);
		}
	}

	// MARK: Method Matcher
	@Override
	public boolean matches(String method) {
		return this.allowedMethods.contains(method);
	}

	@Override
	public String toString() {
		return "MethodMatcherImpl [allowedMethods=" + this.allowedMethods + "]";
	}

}
