package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractExpressionBuilderImpl;

/**
 * Special expression that is empty, and used to start an expression.
 * <p>
 * Using {@link #and(ExpressionBuilder)} and {@link #or(ExpressionBuilder)} will
 * return a copy of the input expression.
 *
 * @author dereekb
 *
 */
public class ExpressionStart extends AbstractExpressionBuilderImpl {

	public ExpressionStart() {}

	@Override
	public ExpressionBuilder and(ExpressionBuilder expression) {
		return expression.copyExpression();
	}

	@Override
	public ExpressionBuilder or(ExpressionBuilder expression) {
		return expression.copyExpression();
	}

	@Override
	public String getExpressionValue() {
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
	public ExpressionBuilder copyExpression() {
		return new ExpressionStart();
	}

}
