package com.dereekb.gae.utilities.web.matcher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * {@link RequestMatcher} that contains other request matchers and matches
 * successfully if any of the matcher's {@link #matches(HttpServletRequest)}
 * returns true.
 * 
 * @author dereekb
 *
 */
public class MultiRequestMatcher
        implements RequestMatcher {

	private List<RequestMatcher> matchers;

	public MultiRequestMatcher(List<RequestMatcher> matchers) {
		this.setMatchers(matchers);
	}

	public List<RequestMatcher> getMatchers() {
		return this.matchers;
	}

	public void setMatchers(List<RequestMatcher> matchers) {
		if (matchers == null || matchers.isEmpty()) {
			throw new IllegalArgumentException("Matchers cannot be null or empty.");
		}

		this.matchers = matchers;
	}

	// MARK: RequestMatcher
	@Override
	public boolean matches(HttpServletRequest request) {
		for (RequestMatcher matcher : this.matchers) {
			if (matcher.matches(request)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString() {
		return "MultiRequestMatcher [matchers=" + this.matchers + "]";
	}

}
