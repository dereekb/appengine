package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractExpressionBuilderImpl;

/**
 * Wraps an expression in braces.
 * <p>
 * Used for creating more complicated expressions to be evaluated in components.
 *
 * @author dereekb
 *
 */
public class ExpressionWrap extends AbstractExpressionBuilderImpl {

	private static final String FORMAT = "(%s)";

	private SearchExpressionBuilder expression;

	public ExpressionWrap(SearchExpressionBuilder expression) {
		this.setExpression(expression);
	}

	public SearchExpressionBuilder getExpression() {
		return this.expression;
	}

	public void setExpression(SearchExpressionBuilder expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Expression cannot be null.");
		}

		this.expression = expression.copyExpression();
	}

	@Override
	public String getQueryExpression() {
		String inner = this.expression.getQueryExpression();
		return String.format(FORMAT, inner);
	}

	@Override
	public boolean isEmpty() {
		return this.expression.isEmpty();
	}

	@Override
	public boolean isComplex() {
		return this.expression.isComplex();
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new ExpressionWrap(this.expression);
	}

	@Override
	public String toString() {
		return "ExpressionWrap [expression=" + this.expression + "]";
	}

}
