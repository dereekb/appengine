package com.dereekb.gae.server.search.document.query.expression.builder;

import com.dereekb.gae.server.search.document.query.expression.Expression;

public interface ExpressionBuilder
        extends Expression {

	public ExpressionBuilder copyExpression();

	public ExpressionBuilder and(ExpressionBuilder expression);

	public ExpressionBuilder or(ExpressionBuilder expression);

}
