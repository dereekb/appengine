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

	private boolean prependServletPath = false;

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

	public boolean getPrependServletPath() {
		return this.prependServletPath;
	}

	public void setPrependServletPath(boolean prependServletPath) {
		this.prependServletPath = prependServletPath;
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
		String method = request.getMethod();

		if (this.matchesMethod(method) == false) {
			return false;
		}

		String requestPath = this.getRequestPath(request);

		if (this.matchesPath(requestPath) == false) {
			return false;
		}

		return true;
	}

	// MARK: Matching
	public boolean matchesMethod(String method) {
		return this.methodMatcher.matches(method);
	}

	public abstract boolean matchesPath(String path);

	// MARK: Utility
	private String getRequestPath(HttpServletRequest request) {
		String url = request.getPathInfo();

		if (this.prependServletPath) {
			url = request.getServletPath() + url;
		}

		if (this.caseSensitive == false) {
			url = url.toLowerCase();
		}

		return url;
	}

}
