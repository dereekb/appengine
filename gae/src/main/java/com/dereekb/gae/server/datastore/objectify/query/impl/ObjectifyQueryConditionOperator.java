package com.dereekb.gae.server.datastore.objectify.query.impl;



/**
 * Operators for Objectify.
 *
 * @author dereekb
 * @see {@link ObjectifyConditionQueryFilter}
 */
public enum ObjectifyQueryConditionOperator {

	Equal("="),
	NotEqual("!="),

	GreaterThan(">"),
	GreaterOrEqualTo(">="),

	LessThan("<"),
	LessOrEqualTo("<="),

	GreaterAndLessThanButNotEqualTo("<>"),

	In("in");

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

	public static ObjectifyQueryConditionOperator fromString(String string) throws IllegalArgumentException {
		ObjectifyQueryConditionOperator op;

		switch (string) {
			case "=":
				op = ObjectifyQueryConditionOperator.Equal;
				break;
			case ">":
				op = ObjectifyQueryConditionOperator.GreaterThan;
				break;
			case ">=":
				op = ObjectifyQueryConditionOperator.GreaterOrEqualTo;
				break;
			case "<":
				op = ObjectifyQueryConditionOperator.LessThan;
				break;
			case "<=":
				op = ObjectifyQueryConditionOperator.LessOrEqualTo;
				break;
			case "!=":
				op = ObjectifyQueryConditionOperator.NotEqual;
				break;
			case "<>":
				op = ObjectifyQueryConditionOperator.GreaterAndLessThanButNotEqualTo;
				break;
			default:
				throw new IllegalArgumentException();
		}

		return op;
    }

}