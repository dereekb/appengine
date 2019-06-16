package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} used for integer query parameters.
 * 
 * @author dereekb
 *
 */
public class BooleanQueryFieldParameter extends AbstractQueryFieldParameter<Boolean> {

	public BooleanQueryFieldParameter() {
		super();
	}

	public BooleanQueryFieldParameter(String field, ExpressionOperator operator, Boolean value) {
		super(field, operator, value);
	}

	public BooleanQueryFieldParameter(String field, Boolean value) {
		super(field, value);
	}

	public BooleanQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public BooleanQueryFieldParameter(AbstractQueryFieldParameter<Boolean> parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public BooleanQueryFieldParameter(String field, AbstractQueryFieldParameter<Boolean> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public static BooleanQueryFieldParameter make(String field,
	                                              Number value)
	        throws IllegalArgumentException {
		BooleanQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = make(field, value.longValue());
		}

		return fieldParameter;
	}

	public static BooleanQueryFieldParameter make(String field,
	                                              Boolean value)
	        throws IllegalArgumentException {
		BooleanQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new BooleanQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public static BooleanQueryFieldParameter make(String field,
	                                              String parameterString)
	        throws IllegalArgumentException {
		BooleanQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new BooleanQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public static BooleanQueryFieldParameter make(String field,
	                                              BooleanQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		BooleanQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new BooleanQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
	}

	// MARK: AbstractQueryFieldParameters
	@Override
	protected String encodeParameterValue(Boolean value) {
		return value.toString();
	}

	@Override
	protected Boolean decodeParameterValue(String value) throws IllegalArgumentException {
		try {
			return new Boolean(value);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
