package com.dereekb.gae.utilities.query.builder.parameters;

import java.util.Map;

/**
 * {@link EncodedQueryParameters} that can be configured using a {@link Map}.
 * 
 * @author dereekb
 *
 */
public interface ConfigurableEncodedQueryParameters
        extends EncodedQueryParameters {

	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException;

}
