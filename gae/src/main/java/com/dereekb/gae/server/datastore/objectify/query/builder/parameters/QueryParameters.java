package com.dereekb.gae.server.datastore.objectify.query.builder.parameters;

import java.util.Map;

/**
 * Represents query parameters that can be mapped to a map of string parameters.
 *
 * @author dereekb
 *
 */
public interface QueryParameters {

	public Map<String, String> getParameters();

}
