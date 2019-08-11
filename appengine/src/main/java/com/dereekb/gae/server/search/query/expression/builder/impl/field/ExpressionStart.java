package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractExpressionBuilderImpl;

/**
 * Special expression that is empty, and used to start an expression.
 * <p>
 * Using {@link #and(SearchExpressionBuilder)} and
 * {@link #or(SearchExpressionBuilder)} will
 * return a copy of the input expression.
 *
 * @author dereekb
 *
 */
public class ExpressionStart extends AbstractExpressionBuilderImpl {

	public ExpressionStart() {}

	@Override
	public SearchExpressionBuilder and(SearchExpressionBuilder expression) {
		return expression.copyExpression();
	}

	@Override
	public SearchExpressionBuilder or(SearchExpressionBuilder expression) {
		return expression.copyExpression();
	}

	@Override
	public String getQueryExpression() {
		return "";
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new ExpressionStart();
	}

	@Override
	public String toString() {
		return "ExpressionStart []";
	}

}
