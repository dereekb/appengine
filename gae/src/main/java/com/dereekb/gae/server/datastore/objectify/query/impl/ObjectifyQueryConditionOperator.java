package com.dereekb.gae.server.datastore.objectify.query.impl;


/**
 * Operators for Objectify.
 *
 * @author dereekb
 * @see {@link ObjectifyConditionQueryFilter}
 */
public enum ObjectifyQueryConditionOperator {

	Equal("="),
	GreaterThan(">"),
	GreaterOrEqualTo(">="),
	LessThan("<"),
	LessOrEqualTo("<="),
	In("in"),
	NotEqual("!="),
	GreaterAndLessThanButNotEqualTo("<>");

	private final String value;

	private ObjectifyQueryConditionOperator(String value) {
		this.value = value;
	}

	public String createFilter(String field){
		return field + " " + this.value;
	}

	public String getValue() {
		return this.value;
	}

}