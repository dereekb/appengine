package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;
import java.util.Set;

import com.dereekb.gae.utilities.collections.list.SetUtility;
import com.dereekb.gae.utilities.data.NumberUtility;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AbstractSetQueryFieldParameter} implementation for {@link String}
 * values.
 * 
 * @author dereekb
 *
 */
public class IntegerSetQueryFieldParameter extends AbstractSetQueryFieldParameter<Integer> {

	public IntegerSetQueryFieldParameter() {
		super();
	}

	public IntegerSetQueryFieldParameter(AbstractQueryFieldParameter<Set<Integer>> parameter)
	        throws IllegalArgumentException {
		super(parameter);
	}

	public IntegerSetQueryFieldParameter(String field, AbstractQueryFieldParameter<Set<Integer>> parameter)
	        throws IllegalArgumentException {
		super(field, parameter);
	}

	public IntegerSetQueryFieldParameter(String field, Collection<Integer> value) throws IllegalArgumentException {
		super(field, value);
	}

	public IntegerSetQueryFieldParameter(String field, String parameterString) throws IllegalArgumentException {
		super(field, parameterString);
	}

	public IntegerSetQueryFieldParameter(String field, Integer value) throws IllegalArgumentException {
		super(field, SetUtility.wrap(value));
	}

	// MARK: AbstractSetQueryFieldParameter
	@Override
	protected String encodeValuesFromString(Collection<Integer> values) {
		return StringUtility.joinValues(values);
	}

	@Override
	protected Collection<Integer> decodeValuesFromString(String value) {
		Collection<String> stringValues = StringUtility.separateValues(value);
		return NumberUtility.integersFromStrings(stringValues);
	}

	@Override
	public String toString() {
		return "IntegerSetQueryFieldParameter []";
	}

}
