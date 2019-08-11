package com.dereekb.gae.server.search.query.expression.builder;

import com.dereekb.gae.server.search.query.expression.SearchExpression;

/**
 * {@Link SearchExpression} builder component.
 *
 * @author dereekb
 *
 */
public interface SearchExpressionBuilder
        extends SearchExpression {

	/**
	 * Copies the current expression and any sub-expressions.
	 *
	 * @return {@link SubExpressionBuilder}. Never {@code null}.
	 */
	public SearchExpressionBuilder copyExpression();

	/**
	 * Joins the two expressions with an AND and returns the result.
	 *
	 * @return {@link SubExpressionBuilder}. Never {@code null}.
	 */
	public SearchExpressionBuilder and(SearchExpressionBuilder expression);

	/**
	 * Joins the two expressions with an OR and returns the result.
	 *
	 * @return {@link SubExpressionBuilder}. Never {@code null}.
	 */
	public SearchExpressionBuilder or(SearchExpressionBuilder expression);

}
