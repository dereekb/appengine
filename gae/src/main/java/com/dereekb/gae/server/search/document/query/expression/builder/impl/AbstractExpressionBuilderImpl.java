package com.dereekb.gae.server.search.document.query.expression.builder.impl;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

public abstract class AbstractExpressionBuilderImpl
        implements ExpressionBuilder {

	public static final String ARGUMENT_SEPARATOR = ",";

	@Override
	public ExpressionBuilder and(ExpressionBuilder expression) {
		return new AndOperation(this, expression);
	}

	@Override
	public ExpressionBuilder or(ExpressionBuilder expression) {
		return new OrOperation(this, expression);
	}

	@Override
	public abstract ExpressionBuilder copyExpression();

	// MARK: Two Element Operation
	static class AndOperation extends TwoElemOperation {

		private static final String FORMAT = "%s AND %s";

		private AndOperation(ExpressionBuilder left, ExpressionBuilder right) {
			super(left, right);
		}

		@Override
		public ExpressionBuilder copyExpression() {
			return new AndOperation(this.left, this.right);
		}

		@Override
		public String getExpressionValue() {
			return super.buildExpressionValue(FORMAT);
		}

	}

	static class OrOperation extends TwoElemOperation {

		private static final String FORMAT = "%s OR %s";

		private OrOperation(ExpressionBuilder left, ExpressionBuilder right) {
			super(left, right);
		}

		@Override
		public ExpressionBuilder copyExpression() {
			return new OrOperation(this.left, this.right);
		}

		@Override
		public boolean isComplex() {
			return true;
		}

		@Override
		public String getExpressionValue() {
			return super.buildExpressionValue(FORMAT);
		}

	}

	static abstract class TwoElemOperation extends AbstractExpressionBuilderImpl {

		protected final ExpressionBuilder left;
		protected final ExpressionBuilder right;

		private TwoElemOperation(ExpressionBuilder left, ExpressionBuilder right) {
			this.left = left.copyExpression();
			this.right = right.copyExpression();
		}

		protected String buildExpressionValue(String format) {
			String leftString = this.left.getExpressionValue();
			String rightString = this.right.getExpressionValue();
			return String.format(format, leftString, rightString);
		}

		@Override
		public boolean isComplex() {
			return this.left.isComplex() && this.right.isComplex();
		}

		@Override
		public ExpressionBuilder and(ExpressionBuilder expression) {
			throw new UnsupportedOperationException("Cannot chain this type of expression.");
		}

		@Override
		public ExpressionBuilder or(ExpressionBuilder expression) {
			throw new UnsupportedOperationException("Cannot chain this type of expression.");
		}

	}

}
