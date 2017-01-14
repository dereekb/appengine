package com.dereekb.gae.server.search.document.query.expression;

/**
 * Search/Query Expression Operators
 *
 * @author dereekb
 *
 */
public enum ExpressionOperator {

	Equal("="),

	/**
	 * For Geopoint locations, this specifies that the given point is outside
	 * the given radius.
	 */
	GreaterThan(">"),
	GreaterOrEqualTo(">="),

	/**
	 * For Geopoint locations, this specifies that the given point is within the
	 * given radius.
	 */
	LessThan("<"),
	LessOrEqualTo("<="),

    // Query Only
	NotEqual("!="),
	GreaterAndLessThanButNotEqualTo("<>"),
	In("in"),

    // Special
	/**
	 * Used only in special cases.
	 */
	IsNull("=n");

	private final String value;

	ExpressionOperator(String value) {
		this.value = value;
	}

	public static ExpressionOperator fromString(String op) {
		ExpressionOperator operation = null;

		switch (op) {
			case "=n":
				operation = IsNull;
				break;
			case "=":
				operation = Equal;
				break;
			case ">":
				operation = GreaterThan;
				break;
			case ">=":
				operation = GreaterOrEqualTo;
				break;
			case "<":
				operation = LessThan;
				break;
			case "<=":
				operation = LessOrEqualTo;
				break;
			case "!=":
				operation = NotEqual;
				break;
			case "<>":
				operation = GreaterAndLessThanButNotEqualTo;
				break;

		}

		return operation;
	}

	public String getValue() {
		return this.value;
	}

	public String createFilter(String field) {
		return field + " " + this.value;
	}

	@Override
	public String toString() {
		return this.value;
	}

	public boolean isComplex() {
		return this.equals(Equal) == false;
	}

}