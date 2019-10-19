package com.dereekb.gae.server.search.query.expression.builder.impl;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;

/**
 * Abstract {@link SearchExpressionBuilder} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractExpressionBuilderImpl
        implements SearchExpressionBuilder {

	public static final String ARGUMENT_SEPARATOR = ",";

	@Override
	public SearchExpressionBuilder and(SearchExpressionBuilder expression) {
		SearchExpressionBuilder result = this;

		if (expression.isEmpty() == false) {
			result = new AndOperation(this, expression);
		}

		return result;
	}

	@Override
	public SearchExpressionBuilder or(SearchExpressionBuilder expression) {
		SearchExpressionBuilder result = this;

		if (expression.isEmpty() == false) {
			result = new OrOperation(this, expression);
		}

		return result;
	}

	@Override
	public abstract SearchExpressionBuilder copyExpression();

	@Override
	public boolean isEmpty() {
		return false;
	}

	// MARK: Two Element Operation
	static class AndOperation extends TwoElemOperation {

		private static final String FORMAT = "%s AND %s";

		private AndOperation(SearchExpressionBuilder left, SearchExpressionBuilder right) {
			super(left, right);
		}

		@Override
		public SearchExpressionBuilder copyExpression() {
			return new AndOperation(this.left, this.right);
		}

		@Override
		public String getQueryExpression() {
			return super.buildExpressionValue(FORMAT);
		}

		@Override
		public String toString() {
			return "AndOperation [left=" + this.left + ", right=" + this.right + "]";
		}

	}

	static class OrOperation extends TwoElemOperation {

		private static final String FORMAT = "%s OR %s";

		private OrOperation(SearchExpressionBuilder left, SearchExpressionBuilder right) {
			super(left, right);
		}

		@Override
		public SearchExpressionBuilder copyExpression() {
			return new OrOperation(this.left, this.right);
		}

		@Override
		public boolean isComplex() {
			return true;
		}

		@Override
		public String getQueryExpression() {
			return super.buildExpressionValue(FORMAT);
		}

		@Override
		public String toString() {
			return "OrOperation [left=" + this.left + ", right=" + this.right + "]";
		}

	}

	static abstract class TwoElemOperation extends AbstractExpressionBuilderImpl {

		protected final SearchExpressionBuilder left;
		protected final SearchExpressionBuilder right;

		private TwoElemOperation(SearchExpressionBuilder left, SearchExpressionBuilder right) {
			this.left = left.copyExpression();
			this.right = right.copyExpression();
		}

		protected String buildExpressionValue(String format) {
			String leftString = this.left.getQueryExpression();
			String rightString = this.right.getQueryExpression();
			return String.format(format, leftString, rightString);
		}

		@Override
		public boolean isEmpty() {
			return this.left.isEmpty() && this.right.isEmpty();
		}

		@Override
		public boolean isComplex() {
			return this.left.isComplex() && this.right.isComplex();
		}

	}

}
