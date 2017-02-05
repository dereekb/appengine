package com.dereekb.gae.utilities.web.matcher;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.dereekb.gae.utilities.collections.set.CaseInsensitiveSet;

/**
 * Matcher that is for catching a URI containing a variable (such as
 * <i>/{type}/foo</i>), then matches against a set of defined types.
 * 
 * Is case insensitive by default.
 * 
 * @author dereekb
 *
 */
public class MultiTypeAntRequestMatcher
        implements RequestMatcher {

	public static final String DEFAULT_TYPE_VARIABLE = "type";
	public static final boolean DEFAULT_CASE_SENSITIVE = false;

	protected final boolean caseSensitive;

	private String pattern;
	private String variable;
	private Set<String> types;

	private AntPathMatcher matcher;

	public MultiTypeAntRequestMatcher(String pattern, Collection<String> types) {
		this(pattern, DEFAULT_TYPE_VARIABLE, types, DEFAULT_CASE_SENSITIVE);
	}

	public MultiTypeAntRequestMatcher(String pattern, Collection<String> types, boolean caseSensitive) {
		this(pattern, DEFAULT_TYPE_VARIABLE, types, caseSensitive);
	}

	public MultiTypeAntRequestMatcher(String pattern,
	        String variable,
	        Collection<String> types,
	        boolean caseSensitive) {
		this(pattern, caseSensitive);
		this.setPattern(pattern);
		this.setVariable(variable);
		this.setTypes(types);
	}

	protected MultiTypeAntRequestMatcher(String pattern, boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
		this.setPattern(pattern);
		this.initMatcher();
	}

	public String getPattern() {
		return this.pattern;
	}

	public void setPattern(String pattern) {
		if (pattern == null || pattern.isEmpty()) {
			throw new IllegalArgumentException("Pattern cannot be null or empty.");
		}

		this.pattern = pattern;
	}

	public boolean isCaseSensitive() {
		return this.caseSensitive;
	}

	public String getVariable() {
		return this.variable;
	}

	public void setVariable(String variable) {
		if (variable == null || variable.isEmpty()) {
			throw new IllegalArgumentException("Variable cannot be null or empty.");
		}

		this.variable = variable;
	}

	public Set<String> getTypes() {
		return this.types;
	}

	public void setTypes(Collection<String> types) {
		if (types == null || types.isEmpty()) {
			throw new IllegalArgumentException("Types set cannot be null or empty.");
		}

		if (this.caseSensitive == false) {
			this.types = new CaseInsensitiveSet(types);
		} else {
			this.types = new HashSet<String>(types);
		}
	}

	private void initMatcher() {
		AntPathMatcher matcher = new AntPathMatcher();
		matcher.setTrimTokens(false);
		matcher.setCaseSensitive(this.caseSensitive);
		this.matcher = matcher;
	}

	// MARK: RequestMatcher
	@Override
	public boolean matches(HttpServletRequest request) {
		String requestPath = this.getRequestPath(request);
		return this.matches(requestPath);
	}

	private String getRequestPath(HttpServletRequest request) {
		String url = request.getServletPath();

		if (request.getPathInfo() != null) {
			url += request.getPathInfo();
		}

		if (!this.caseSensitive) {
			url = url.toLowerCase();
		}

		return url;
	}

	// MARK: Matching
	public boolean matches(String path) {
		boolean matchesPath = this.matcher.match(this.pattern, path);

		if (matchesPath) {
			matchesPath = this.matchesVariables(path);
		}

		return matchesPath;
	}

	protected boolean matchesVariables(String path) {
		Map<String, String> pathVariables = this.matcher.extractUriTemplateVariables(this.pattern, path);
		return this.matchesVariables(pathVariables);
	}

	protected boolean matchesVariables(Map<String, String> pathVariables) {
		String variableValue = pathVariables.get(this.variable);
		return this.types.contains(variableValue);
	}

	@Override
	public String toString() {
		return "MultiTypeAntRequestMatcher [caseSensitive=" + this.caseSensitive + ", pattern=" + this.pattern
		        + ", variable=" + this.variable + ", types=" + this.types + "]";
	}

}
