package com.dereekb.gae.server.search.document.query.builder.fields;

public enum DocumentQueryOperator {
	
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
	
	DocumentQueryOperator(String value){
		this.value = value;
	}

	public String toString() {
		return this.value;
	}
}