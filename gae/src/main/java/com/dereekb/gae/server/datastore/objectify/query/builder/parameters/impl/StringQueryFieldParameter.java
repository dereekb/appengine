package com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl;

/**
 * {@link AbstractQueryFieldParameter} for a {@link String} value.
 * 
 * @author dereekb
 *
 */
public class StringQueryFieldParameter extends AbstractQueryFieldParameter<String> {

	@Override
	public String getParameterValue() {
		return this.value;
	}

	@Override
	public void setParameterValue(String value) throws IllegalArgumentException {
		this.value = value;
	}

}
