package com.dereekb.gae.utilities.query.builder.parameters;

import java.util.Map;

/**
 * Represents query parameters that can be mapped to a map of string parameters.
 *
 * @author dereekb
 *
 */
public interface EncodedQueryParameters {

	public Map<String, String> getParameters();

}
