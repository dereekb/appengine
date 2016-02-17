package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;


public class BooleanField extends AtomField {

	public static final String TRUE_ATOM = "true";
	public static final String FALSE_ATOM = "false";

	public BooleanField(String name, Boolean value) {
		super(name, atomValueForBoolean(value));
	}

	public static String atomValueForBoolean(boolean value) {
		return (value) ? TRUE_ATOM : FALSE_ATOM;
	}

	@Override
	public String toString() {
		return "BooleanField [value=" + this.value + ", name=" + this.name + "]";
	}

}
