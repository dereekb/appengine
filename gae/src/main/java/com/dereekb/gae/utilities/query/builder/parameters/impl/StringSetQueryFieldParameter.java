package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.data.StringUtility;

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

	public StringSetQueryFieldParameter(String field, Collection<String> value) throws IllegalArgumentException {
		super(field, value);
	}

	public StringSetQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}
	
	public static StringSetQueryFieldParameter makeWithSingle(String field, String singleValue) {
		return new StringSetQueryFieldParameter(field, SetUtility.wrap(singleValue));
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
