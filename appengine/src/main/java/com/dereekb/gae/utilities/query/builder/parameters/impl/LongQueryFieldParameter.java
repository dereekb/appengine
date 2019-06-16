package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} used for {@link Long} query parameters.
 * 
 * @author dereekb
 *
 */
public class LongQueryFieldParameter extends AbstractQueryFieldParameter<Long> {

	public LongQueryFieldParameter() {
		super();
	}

	public LongQueryFieldParameter(String field, ExpressionOperator operator, Long value) {
		super(field, operator, value);
	}

	public LongQueryFieldParameter(String field, Long value) {
		super(field, value);
	}

	public LongQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public LongQueryFieldParameter(AbstractQueryFieldParameter<Long> parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public LongQueryFieldParameter(String field, AbstractQueryFieldParameter<Long> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public static LongQueryFieldParameter make(String field,
	                                           Number value)
	        throws IllegalArgumentException {
		LongQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = make(field, value.longValue());
		}

		return fieldParameter;
	}

	public static LongQueryFieldParameter make(String field,
	                                           Long value)
	        throws IllegalArgumentException {
		LongQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = new LongQueryFieldParameter(field, value);
		}

		return fieldParameter;
	}

	public static LongQueryFieldParameter make(String field,
	                                           String parameterString)
	        throws IllegalArgumentException {
		LongQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new LongQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public static LongQueryFieldParameter make(String field,
	                                           LongQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		LongQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new LongQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
	}

	// MARK: AbstractQueryFieldParameters
	@Override
	protected String encodeParameterValue(Long value) {
		return value.toString();
	}

	@Override
	protected Long decodeParameterValue(String value) throws IllegalArgumentException {
		try {
			return new Long(value);
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

}
