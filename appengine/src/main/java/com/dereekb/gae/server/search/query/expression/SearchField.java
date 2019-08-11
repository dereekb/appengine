package com.dereekb.gae.server.search.query.expression;

/**
 * {@link SearchExpression} extension for field values.
 *
 * @author dereekb
 *
 */
public interface SearchField
        extends SearchExpression {

	/**
	 * Returns the field name.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getName();

}
