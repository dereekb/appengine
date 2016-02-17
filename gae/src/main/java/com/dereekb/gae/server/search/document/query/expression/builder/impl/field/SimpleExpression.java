package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.document.query.DocumentQueryExpressionSanitizer;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractExpressionBuilderImpl;
import com.dereekb.gae.server.search.document.query.impl.DocumentQueryExpressionSanitizerImpl;

/**
 * Contains a simple query expression.
 * <p>
 * Input expression is sanitized.
 *
 * @author dereekb
 *
 */
public class SimpleExpression extends AbstractExpressionBuilderImpl {

	private static final DocumentQueryExpressionSanitizer SANITIZER = new DocumentQueryExpressionSanitizerImpl();

	private String expression;

	public SimpleExpression(String expression) {
		this.setExpression(expression);
	}

	private SimpleExpression(SimpleExpression expression) {
		this.setSanitizedExpression(expression.expression);
	}

	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = SANITIZER.sanitizeExpression(expression);
	}

	private void setSanitizedExpression(String expression) {
		this.expression = expression;
	}

	@Override
	public String getExpressionValue() {
		return this.expression;
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new SimpleExpression(this);
	}

}
