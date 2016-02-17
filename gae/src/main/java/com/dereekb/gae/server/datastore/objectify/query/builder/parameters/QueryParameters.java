package com.dereekb.gae.server.datastore.objectify.query.builder.parameters;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;

/**
 * Represents query parameters that can be mapped to a map of string parameters.
 *
 * @author dereekb
 *
 */
public interface QueryParameters
        extends ObjectifyQueryRequestLimitedConfigurer {

	public Map<String, String> getParameters();

}
