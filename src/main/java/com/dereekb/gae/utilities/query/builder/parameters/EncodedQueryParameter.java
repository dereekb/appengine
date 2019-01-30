package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;

/**
 * String-encoded query parameter.
 * 
 * @author dereekb
 *
 */
public interface EncodedQueryParameter
        extends EncodedValueExpressionOperatorPair {

	/**
	 * Returns the ordering specified by the parameter.
	 * 
	 * @return {@link QueryResultsOrdering}, or {@code null} if not defined.
	 */
	public QueryResultsOrdering getOrdering();

	/**
	 * Returns the second filter, if available.
	 * 
	 * @return {@link EncodedValueExpressionOperatorPair}, or {@code null} if
	 *         not defined.
	 */
	public EncodedValueExpressionOperatorPair getSecondFilter();

}