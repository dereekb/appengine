package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.AbstractExpressionBuilderImpl;

public class RawExpression extends AbstractExpressionBuilderImpl {

	private String expression;

	public RawExpression(String expression) {
		this.expression = expression;
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	// MARK: AbstractExpressionBuilderImpl
	@Override
	public String getExpressionValue() {
		return this.expression;
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new RawExpression(this.expression);
	}

	@Override
	public String toString() {
		return "RawExpression [expression=" + this.expression + "]";
	}

}
