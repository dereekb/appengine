package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} used for integer query parameters.
 * 
 * @author dereekb
 *
 */
public class ModelKeyQueryFieldParameter extends AbstractQueryFieldParameter<Integer> {

	public ModelKeyQueryFieldParameter() {
		super();
	}

	public ModelKeyQueryFieldParameter(String field, ExpressionOperator operator, Integer value) {
		super(field, operator, value);
	}

	public ModelKeyQueryFieldParameter(String field, Integer value) {
		super(field, value);
	}

	public ModelKeyQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
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
