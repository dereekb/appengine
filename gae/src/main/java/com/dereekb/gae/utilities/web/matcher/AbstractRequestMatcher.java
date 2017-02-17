package com.dereekb.gae.utilities.web.matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

import com.dereekb.gae.utilities.web.matcher.method.RequestMethodMatcher;
import com.dereekb.gae.utilities.web.matcher.method.impl.AllowAllRequestMethodMatcher;

/**
 * Abstract {@link RequestMatcher} implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractRequestMatcher
        implements RequestMatcher {

	public static final boolean DEFAULT_CASE_SENSITIVE = false;

	protected final boolean caseSensitive;
	private RequestMethodMatcher methodMatcher;

	public AbstractRequestMatcher() {
		this(DEFAULT_CASE_SENSITIVE);
	}

	public AbstractRequestMatcher(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		this.allowAllMethods();
	}

	public AbstractRequestMatcher(RequestMethodMatcher methodMatcher, boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		this.setMethodMatcher(methodMatcher);
	}

	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

	public void allowAllMethods() {
		this.setMethodMatcher(AllowAllRequestMethodMatcher.SINGLETON);
	}

	public RequestMethodMatcher getMethodMatcher() {
		return this.methodMatcher;
	}

	public void setMethodMatcher(RequestMethodMatcher methodMatcher) throws IllegalArgumentException {
		if (methodMatcher == null) {
			throw new IllegalArgumentException("MethodMatcher cannot be null.");
		}

		this.methodMatcher = methodMatcher;
	}

	// MARK: RequestMatcher
	@Override
	public final boolean matches(HttpServletRequest request) {
		String requestPath = this.getRequestPath(request);
		String method = request.getMethod();
		return this.matchesMethod(method) && this.matchesPath(requestPath);
	}

	// MARK: Matching
	public boolean matchesMethod(String method) {
		return this.methodMatcher.matches(method);
	}

	public abstract boolean matchesPath(String path);

	// MARK: Utility
	private String getRequestPath(HttpServletRequest request) {
		String url = request.getServletPath();

		if (request.getPathInfo() != null) {
			url += request.getPathInfo();
		}

		if (this.caseSensitive == false) {
			url = url.toLowerCase();
		}

		return url;
	}

}
