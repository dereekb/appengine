package com.dereekb.gae.utilities.query.builder.parameters.impl;

import java.util.Collection;

import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AbstractSetQueryFieldParameter} implementation for {@link String}
 * values.
 * 
 * @author dereekb
 *
 */
public class StringSetQueryFieldParameter extends AbstractSetQueryFieldParameter<String> {

	@Override
	protected String encodeValuesFromString(Collection<String> values) {
		return StringUtility.joinValues(values);
	}

	@Override
	protected Collection<String> decodeValuesFromString(String value) {
		return StringUtility.separateValues(value);
	}

}
