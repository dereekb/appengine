package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractExpressionBuilderImpl;


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
	public boolean isComplex() {
		return this.expression.isComplex();
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new ExpressionWrap(this.expression);
	}

}
