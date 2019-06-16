package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * {@link AbstractSetQueryFieldParameter} implementation for {@link String}
 * values.
 *
 * @author dereekb
 *
 */
public class StringSetQueryFieldParameter extends AbstractSetQueryFieldParameter<String> {

	public StringSetQueryFieldParameter() {
		super();
	}

	public StringSetQueryFieldParameter(AbstractQueryFieldParameter<Set<String>> parameter)
	        throws IllegalArgumentException {
		super(parameter);
	}

	public StringSetQueryFieldParameter(String field, AbstractQueryFieldParameter<Set<String>> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public StringSetQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString, ExpressionOperator.EQUAL);
	}

	@Deprecated
	public StringSetQueryFieldParameter(String field, Collection<String> value) throws IllegalArgumentException {
		super(field, value);
	}

	public static StringSetQueryFieldParameter tryMakeWithSingle(String field,
	                                                             String value)
	        throws IllegalArgumentException {
		StringSetQueryFieldParameter fieldParameter = null;

		if (value != null) {
			fieldParameter = StringSetQueryFieldParameter.makeWithSingle(field, value);
		}

		return fieldParameter;
	}

	public static StringSetQueryFieldParameter makeWithSingle(String field,
	                                                          String singleValue) {
		return new StringSetQueryFieldParameter(field, singleValue);
	}

	public static StringSetQueryFieldParameter make(String field,
	                                                String parameterString)
	        throws IllegalArgumentException {
		StringSetQueryFieldParameter fieldParameter = null;

		if (parameterString != null) {
			fieldParameter = new StringSetQueryFieldParameter(field, parameterString);
		}

		return fieldParameter;
	}

	public static StringSetQueryFieldParameter make(String field,
	                                                StringSetQueryFieldParameter parameter)
	        throws IllegalArgumentException {
		StringSetQueryFieldParameter fieldParameter = null;

		if (parameter != null) {
			fieldParameter = new StringSetQueryFieldParameter(field, parameter);
		}

		return fieldParameter;
	}

	public static StringSetQueryFieldParameter make(String field,
	                                                Collection<String> values)
	        throws IllegalArgumentException {
		StringSetQueryFieldParameter fieldParameter = null;

		if (values != null) {
			fieldParameter = new StringSetQueryFieldParameter(field, values);
		}

		return fieldParameter;
	}

	// MARK: AbstractSetQueryFieldParameter
	@Override
	protected String encodeValuesFromString(Collection<String> values) {
		return StringUtility.joinValues(values);
	}

	@Override
	protected Collection<String> decodeValuesFromString(String value) {
		return StringUtility.separateValues(value);
	}

}
