package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.AbstractExpressionBuilderImpl;

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

	private ExpressionBuilder expression;

	public ExpressionWrap(ExpressionBuilder expression) {
		this.setExpression(expression);
    }

	public ExpressionBuilder getExpression() {
		return this.expression;
	}

	public void setExpression(ExpressionBuilder expression) {
		if (expression == null) {
			throw new IllegalArgumentException("Expression cannot be null.");
		}

		this.expression = expression.copyExpression();
	}

	@Override
    public String getExpressionValue() {
		String inner = this.expression.getExpressionValue();
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
	public ExpressionBuilder copyExpression() {
		return new ExpressionWrap(this.expression);
	}

	@Override
	public String toString() {
		return "ExpressionWrap [expression=" + this.expression + "]";
	}

}
