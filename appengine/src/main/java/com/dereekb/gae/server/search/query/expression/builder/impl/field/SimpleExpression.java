package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractExpressionBuilderImpl;
import com.dereekb.gae.server.search.query.utility.SearchQueryExpressionSanitizer;
import com.dereekb.gae.server.search.query.utility.impl.SearchQueryExpressionSanitizerImpl;

/**
 * Contains a simple query expression.
 * <p>
 * Input expression is sanitized.
 *
 * @author dereekb
 *
 */
public class SimpleExpression extends AbstractExpressionBuilderImpl {

	private static final SearchQueryExpressionSanitizer SANITIZER = new SearchQueryExpressionSanitizerImpl();

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
	public String getQueryExpression() {
		return this.expression;
	}

	@Override
	public boolean isComplex() {
		return false;
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new SimpleExpression(this);
	}

}
