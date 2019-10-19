package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractExpressionBuilderImpl;

public class NotExpression extends AbstractExpressionBuilderImpl {

	private static final String FORMAT = "NOT %s";

	private SearchExpressionBuilder expressionBuilder;

	public NotExpression(SearchExpressionBuilder expressionBuilder) {
		this.setExpressionBuilder(expressionBuilder);
	}

	public SearchExpressionBuilder getExpressionBuilder() {
		return this.expressionBuilder;
	}

	public void setExpressionBuilder(SearchExpressionBuilder expressionBuilder) {
		this.expressionBuilder = expressionBuilder.copyExpression();
	}

	@Override
	public String getQueryExpression() {
		String expressionString = this.expressionBuilder.getQueryExpression();
		return String.format(FORMAT, expressionString);
	}

	@Override
	public boolean isComplex() {
		return true;
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new NotExpression(this.expressionBuilder);
	}

}
