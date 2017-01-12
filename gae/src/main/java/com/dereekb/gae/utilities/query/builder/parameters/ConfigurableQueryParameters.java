package com.dereekb.gae.utilities.query.builder.parameters;

import java.util.Map;

/**
 * {@link QueryParameters} that can be configured using a {@link Map}.
 * 
 * @author dereekb
 *
 */
public interface ConfigurableQueryParameters
        extends QueryParameters {

	public void setParameters(Map<String, String> parameters) throws IllegalArgumentException;

}
