package com.dereekb.gae.server.search.document.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.AbstractTextField;

public class AtomField extends AbstractTextField {

	public static String NONE_VALUE = "NONE";

	public AtomField(String name, Number value) {
		super(name, ((value != null) ? value.toString() : null), true);
	}

	public AtomField(String name, String value) {
		super(name, value, true);
	}

	@Override
	public void setSpecificText(boolean specificText) {
		throw new UnsupportedOperationException("Cannot change Atom field.");
	}

	@Override
	public ExpressionBuilder copyExpression() {
		return new AtomField(this.name, this.value);
	}

	@Override
	public String toString() {
		return "AtomField [value=" + this.value + ", specificText=" + this.specificText + ", name=" + this.name + "]";
	}

}
