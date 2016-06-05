package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} for a {@link String} value.
 *
 * @author dereekb
 *
 */
public class StringQueryFieldParameter extends AbstractQueryFieldParameter<String> {

	public StringQueryFieldParameter() {
		super();
	}

	public StringQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		this.setField(field);
		this.setParameterString(parameterString);
	}

	public StringQueryFieldParameter(String field, ExpressionOperator operator, String value) {
		super(field, operator, value);
	}

	// MARK: AbstractQueryFieldParameter
	@Override
	public String getParameterValue() {
		return this.value;
	}

	@Override
	public void setParameterValue(String value) throws IllegalArgumentException {
		this.value = value;
	}

}
