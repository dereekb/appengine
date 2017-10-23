package com.dereekb.gae.utilities.query.builder.parameters;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * Abstract interface that has an {@link ExpressionOperator}.
 * 
 * @author dereekb
 *
 */
public abstract interface ExpressionOperatorParameter {

	/**
	 * Returns the operator specified by the parameter.
	 * 
	 * @return {@link ExpressionOperator}, or {@code null} if not defined.
	 */
	public ExpressionOperator getOperator();

}
