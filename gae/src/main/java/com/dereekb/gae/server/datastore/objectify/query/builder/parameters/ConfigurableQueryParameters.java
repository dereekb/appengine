package com.dereekb.gae.server.datastore.objectify.query.builder.parameters;

import java.util.Map;

public interface ConfigurableQueryParameters
        extends QueryParameters {

	public void setParameters(Map<String, String> parameters);

}
