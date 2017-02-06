package com.dereekb.gae.server.search.document.query.expression.builder.impl;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;

public abstract class AbstractExpressionBuilderImpl
        implements ExpressionBuilder {

	public static final String ARGUMENT_SEPARATOR = ",";

	@Override
	public ExpressionBuilder and(ExpressionBuilder expression) {
		ExpressionBuilder result = this;

		if (expression.isEmpty() == false) {
			result = new AndOperation(this, expression);
		}

		return result;
	}

	@Override
	public ExpressionBuilder or(ExpressionBuilder expression) {
		ExpressionBuilder result = this;

		if (expression.isEmpty() == false) {
			result = new OrOperation(this, expression);
		}

		return result;
	}

	@Override
	public abstract ExpressionBuilder copyExpression();

	@Override
	public boolean isEmpty() {
		return false;
	}

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

		@Override
		public String toString() {
			return "AndOperation [left=" + this.left + ", right=" + this.right + "]";
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

		@Override
		public String toString() {
			return "OrOperation [left=" + this.left + ", right=" + this.right + "]";
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
		public boolean isEmpty() {
			return this.left.isEmpty() && this.right.isEmpty();
		}

		@Override
		public boolean isComplex() {
			return this.left.isComplex() && this.right.isComplex();
		}

	}

}
