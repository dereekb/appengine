package com.dereekb.gae.server.search.document.query;

/**
 * Sanitizes document query expression strings.
 *
 * @author dereekb
 *
 */
public interface DocumentQueryExpressionSanitizer {

	/**
	 * Sanitizes the input string.
	 *
	 * @param expression
	 *            {@link String} expression. Never {@code null}.
	 * @return sanitized {@link String}. Never {@code null}.
	 */
	public String sanitizeExpression(String expression);

}
