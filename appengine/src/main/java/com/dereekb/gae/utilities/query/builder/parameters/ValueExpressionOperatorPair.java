package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * Value and {@link ExpressionOperator} pair.
 * 
 * @author dereekb
 *
 * @param <T>
 *            value type
 */
public interface ValueExpressionOperatorPair<T>
        extends ExpressionOperatorParameter {

	/**
	 * Returns the value.
	 * 
	 * @return value, or {@code null}.
	 */
	public T getValue();

}
