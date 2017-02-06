package com.dereekb.gae.server.search.document.query.expression;

/**
 * Search/Query Expression Operators
 *
 * @author dereekb
 *
 */
public enum ExpressionOperator {

	EQUAL("="),

	/**
	 * For Geopoint locations, this specifies that the given point is outside
	 * the given radius.
	 */
	GREATER_THAN(">"),
	GREATER_OR_EQUAL_TO(">="),

	/**
	 * For Geopoint locations, this specifies that the given point is within the
	 * given radius.
	 */
	LESS_THAN("<"),
	LESS_OR_EQUAL_TO("<="),

    // Query Only
	NOT_EQUAL("!="),
	GREATER_OR_LESS_BUT_NOT_EQUAL_TO("<>"),

	/**
	 * Used to check that one or more items of one collection are in another.
	 * 
	 * I.E. will check if EITHER object A or B are referenced within a
	 * collection.
	 */
	IN("in"),

    // Special
	/**
	 * Used only in cases where is null equivalence needs to be checked.
	 */
	IS_NULL("=n"),

	/**
	 * Used when no comparison operation needs to take place. Useful for
	 * instances where ordering is needed but no value filtering.
	 */
	NO_OP("_");

	private final String value;

	ExpressionOperator(String value) {
		this.value = value;
	}

	public static ExpressionOperator fromString(String op) {
		ExpressionOperator operation = null;

		switch (op) {
			case "=":
				operation = EQUAL;
				break;
			case ">":
				operation = GREATER_THAN;
				break;
			case ">=":
				operation = GREATER_OR_EQUAL_TO;
				break;
			case "<":
				operation = LESS_THAN;
				break;
			case "<=":
				operation = LESS_OR_EQUAL_TO;
				break;
			case "!=":
				operation = NOT_EQUAL;
				break;
			case "<>":
				operation = GREATER_OR_LESS_BUT_NOT_EQUAL_TO;
				break;
			case "in":
				operation = IN;
				break;
			case "=n":
				operation = IS_NULL;
				break;
			case "_":
				operation = NO_OP;
				break;
			default:
				throw new IllegalArgumentException("No operator available for key '" + op + "'.");
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
		return this.equals(EQUAL) == false;
	}

}