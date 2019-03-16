package com.dereekb.gae.utilities.web.matcher;

/**
 * Single string matching {@link RequestMatcher}.
 * 
 * @author dereekb
 *
 */
public class CaseInsensitiveStringRequestMatcher extends AbstractRequestMatcher {

	private String path;

	public CaseInsensitiveStringRequestMatcher(String path) {
		super(true);	// Set case sensitivity to false to prevent URL
		            	// toLowerCase().
		this.setPath(path);
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		if (path == null || path.isEmpty()) {
			throw new IllegalArgumentException("Path cannot be null or empty.");
		}

		this.path = path;
	}

	@Override
	public boolean isCaseSensitive() {
		return false;
	}

	// MARK: AbstractRequestMatcher
	@Override
	public boolean matchesPath(String path) {
		return this.path.equalsIgnoreCase(path);
	}

	@Override
	public String toString() {
		return "CaseInsensitiveStringRequestMatcher [path=" + this.path + "]";
	}

}
