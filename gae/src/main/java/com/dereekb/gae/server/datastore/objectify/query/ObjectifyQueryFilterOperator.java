package com.dereekb.gae.server.datastore.objectify.query;

public enum ObjectifyQueryFilterOperator {
	
	Equal("="),
	GreaterThan(">"),
	GreaterOrEqualTo(">="),
	LessThan("<"),
	LessOrEqualTo("<="),
	In("in"),
	NotEqual("!="),
	GreaterAndLessThanButNotEqualTo("<>");
	
	private final String value;
	
	ObjectifyQueryFilterOperator(String value){
		this.value = value;
	}
	
	public String createFilter(String field){
		return field + " " + this.value; 
	}

	public String getValue() {
		return value;
	}
}