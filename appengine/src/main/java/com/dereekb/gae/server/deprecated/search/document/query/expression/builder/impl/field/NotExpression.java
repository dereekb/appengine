package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.deprecated.search.document.query.expression.builder.impl.AbstractExpressionBuilderImpl;


public class NotExpression extends AbstractExpressionBuilderImpl {

	private static final String FORMAT = "NOT %s";

	private ExpressionBuilder expressionBuilder;

	public NotExpression(ExpressionBuilder expressionBuilder) {
		this.setExpressionBuilder(expressionBuilder);
	}

	public ExpressionBuilder getExpressionBuilder() {
		return this.expressionBuilder;
	}

	public void setExpressionBuilder(ExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder.copyExpression();
	}

	@Override
	public String getExpressionValue() {
		String expressionString = this.expressionBuilder.getExpressionValue();
		return String.format(FORMAT, expressionString);
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new NotExpression(this.expressionBuilder);
	}

}
