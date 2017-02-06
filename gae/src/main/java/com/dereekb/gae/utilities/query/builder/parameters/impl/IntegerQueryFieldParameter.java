package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} used for integer query parameters.
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

	public IntegerQueryFieldParameter(AbstractQueryFieldParameter<Integer> parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public IntegerQueryFieldParameter(String field, AbstractQueryFieldParameter<Integer> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public static IntegerQueryFieldParameter make(String field,
	                                              Integer value)
	        throws IllegalArgumentException {
		IntegerQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new IntegerQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public static IntegerQueryFieldParameter make(String field,
	                                              String parameterString)
	        throws IllegalArgumentException {
		IntegerQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new IntegerQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public static IntegerQueryFieldParameter make(String field,
	                                              IntegerQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		IntegerQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new IntegerQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
	}

	// MARK: AbstractQueryFieldParameters
	@Override
	protected String getParameterValue() {
		return this.getValue().toString();
	}

	@Override
	protected void setParameterValue(String value) throws IllegalArgumentException {
		try {
			this.setValue(new Integer(value));
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
