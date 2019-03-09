package com.dereekb.gae.server.search.document.query.expression;

/**
 * Search/Query Expression Operators
 *
 * @author dereekb
 *
 */
public enum ExpressionOperator {

	EQUAL("=", false),

	/**
	 * For Geopoint locations, this specifies that the given point is outside
	 * the given radius.
	 */
	GREATER_THAN(">", true),
	GREATER_OR_EQUAL_TO(">=", true),

	/**
	 * For Geopoint locations, this specifies that the given point is within the
	 * given radius.
	 */
	LESS_THAN("<", true),
	LESS_OR_EQUAL_TO("<=", true),

    // Query Only
	/**
	 *
	 * @deprecated No longer supported by Google Cloud, for now.
	 */
	NOT_EQUAL("!=", false),
	GREATER_OR_LESS_BUT_NOT_EQUAL_TO("<>", true),

	/**
	 * Used to check that one or more items of one collection are in another.
	 *
	 * I.E. will check if EITHER object A <i>OR</i> B are referenced within a
	 * collection.
	 *
	 * @deprecated No longer supported by Google Cloud, for now.
	 */
	@Deprecated
	IN("in", false),

    // Special
	/**
	 * Used only in cases where is null equivalence needs to be checked.
	 */
	IS_NULL("=n", false),

	/**
	 * Used when no comparison operation needs to take place. Useful for
	 * instances where ordering is needed but no value filtering.
	 */
	NO_OP("_", false);

	private final String value;
	private final boolean inequality;

	ExpressionOperator(String value, boolean inequality) {
		this.value = value;
		this.inequality = inequality;
	}

	public boolean isEquality() {
		return !this.inequality;
	}

	public boolean isInequality() {
		return this.inequality;
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

	@Deprecated
	public boolean isComplex() {
		return this.equals(EQUAL) == false;
	}

}