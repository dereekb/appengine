package com.dereekb.gae.server.search.query.expression.builder.impl.field;

import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.AbstractTextField;
import com.dereekb.gae.utilities.data.ValueUtility;

/**
 * Atom text field.
 * <p>
 * Atoms are matched exactly and contain specific values.
 *
 * @author dereekb
 *
 */
public class AtomField extends AbstractTextField {

	public static String EMPTY_VALUE = "";

	public AtomField(String name, Object value) {
		super(name, ((value != null) ? value.toString() : null), true);
	}

	public AtomField(String name, String value) {
		super(name, value, true);
	}

	@Override
	public void setValue(String value) {
		super.setValue(ValueUtility.defaultTo(value, EMPTY_VALUE));
	}

	@Override
	public void setSpecificText(boolean specificText) {
		throw new UnsupportedOperationException("Cannot change Atom field.");
	}

	@Override
	public SearchExpressionBuilder copyExpression() {
		return new AtomField(this.name, this.value);
	}

	@Override
	public String toString() {
		return "AtomField [value=" + this.value + ", specificText=" + this.specificText + ", name=" + this.name + "]";
	}

}
