package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * Query parameter.
 * 
 * @author dereekb
 *
 */
public interface Parameter {

	/**
	 * Returns the string representation of the value.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getValue();

	/**
	 * Returns the operator specified by the parameter.
	 * 
	 * @return {@link ExpressionOperator}, or {@code null} if not defined.
	 */
	public ExpressionOperator getOperator();

	/**
	 * Returns the ordering specified by the parameter.
	 * 
	 * @return {@link QueryResultsOrdering}, or {@code null} if not defined.
	 */
	public QueryResultsOrdering getOrdering();

}