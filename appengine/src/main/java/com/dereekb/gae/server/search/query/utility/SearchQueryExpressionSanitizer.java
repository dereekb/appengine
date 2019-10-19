package com.dereekb.gae.server.search.query.utility;

/**
 * Sanitizes document query expression strings.
 *
 * @author dereekb
 *
 */
public interface SearchQueryExpressionSanitizer {

	/**
	 * Sanitizes the input string.
	 *
	 * @param expression
	 *            {@link String} expression. Never {@code null}.
	 * @return sanitized {@link String}. Never {@code null}.
	 */
	public String sanitizeExpression(String expression);

}
