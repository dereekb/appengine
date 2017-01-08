package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} used in Objectify queries.
 * 
 * @author dereekb
 *
 */
public class IntegerQueryFieldParameter extends AbstractQueryFieldParameter<Integer> {

	public IntegerQueryFieldParameter() {
		super();
	}

	public IntegerQueryFieldParameter(String field, ExpressionOperator operator, Integer value) {
		super(field, operator, value);
	}

	public IntegerQueryFieldParameter(String field, Integer value) {
		super(field, value);
	}

	public IntegerQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	// MARK: AbstractQueryFieldParameters
	@Override
	public String getParameterValue() {
		return this.value.toString();
	}

	@Override
	public void setParameterValue(String value) throws IllegalArgumentException {
		try {
			this.value = new Integer(value);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
