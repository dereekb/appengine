package com.dereekb.gae.server.search.query.expression;

import com.dereekb.gae.server.search.query.SearchServiceQueryExpression;

/**
 * Basic search expression.
 *
 * @author dereekb
 *
 */
public interface SearchExpression
        extends SearchServiceQueryExpression {

	/**
	 * Whether or not this is an empty expression.
	 *
	 * @return {@code true} if empty.
	 */
	public boolean isEmpty();

	/**
	 * Whether or not this expression is deemed complex.
	 * <p>
	 * The idea behind complex expressions comes from Google App Engine's Search
	 * API.
	 *
	 * @return {@code true} if complex.
	 */
	public boolean isComplex();

}
