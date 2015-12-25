package com.dereekb.gae.server.search.document.query.expression;

public enum ExpressionOperator {

	Equal("="),

	/**
	 * For Geopoint locations, this specifies that the given point is outside the given radius.
	 */
	GreaterThan(">"),
	GreaterOrEqualTo(">="),

	/**
	 * For Geopoint locations, this specifies that the given point is within the given radius.
	 */
	LessThan("<"),
	LessOrEqualTo("<=");

	private final String value;

	ExpressionOperator(String value){
		this.value = value;
	}

	@Override
    public String toString() {
		return this.value;
	}

	public boolean isComplex() {
		return this.equals(Equal) == false;
	}

}