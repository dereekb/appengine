package com.dereekb.gae.utilities.web.matcher;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

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

	public AbstractRequestMatcher() {
		this(DEFAULT_CASE_SENSITIVE);
	}

	public AbstractRequestMatcher(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

	// MARK: RequestMatcher
	@Override
	public boolean matches(HttpServletRequest request) {
		String requestPath = this.getRequestPath(request);
		return this.matches(requestPath);
	}

	public abstract boolean matches(String path);

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
