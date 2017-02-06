package com.dereekb.gae.utilities.query.builder.parameters.impl;

import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;

/**
 * {@link AbstractQueryFieldParameter} used for {@link String} query parameters.
 *
 * @author dereekb
 *
 */
public class StringQueryFieldParameter extends AbstractQueryFieldParameter<String> {

	protected StringQueryFieldParameter() {
		super();
	}

	public StringQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		// Configure here; ambignous otherwise if passed to super.
		this.setField(field);
		this.setParameterString(parameterString);
	}

	public StringQueryFieldParameter(String field, ExpressionOperator operator, String value) {
		super(field, operator, value);
	}

	public StringQueryFieldParameter(String field, AbstractQueryFieldParameter<String> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public StringQueryFieldParameter(AbstractQueryFieldParameter<String> parameter) throws IllegalArgumentException {
		super(parameter);
	}

	public static StringQueryFieldParameter make(String field,
	                                             String parameterString)
	        throws IllegalArgumentException {
		StringQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new StringQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public static StringQueryFieldParameter make(String field,
	                                             StringQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		StringQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new StringQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
	}

	public static StringQueryFieldParameter makeEqualsQuery(String field,
	                                                        String ownerId)
	        throws IllegalArgumentException {
		return new StringQueryFieldParameter(field, ExpressionOperator.EQUAL, ownerId);
	}

	// MARK: AbstractQueryFieldParameter
	@Override
	protected String getParameterValue() {
		return this.getValue();
	}

	@Override
	protected void setParameterValue(String value) throws IllegalArgumentException {
		this.setValue(value);
	}

}
