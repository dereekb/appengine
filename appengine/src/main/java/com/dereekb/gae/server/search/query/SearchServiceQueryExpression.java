package com.dereekb.gae.server.search.query;

/**
 * Search service query expression.
 *
 * @author dereekb
 *
 */
public interface SearchServiceQueryExpression {

	/**
	 * Returns the full expression string's value.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getQueryExpression();

}
