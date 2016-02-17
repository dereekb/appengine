package com.dereekb.gae.server.datastore.objectify.query.builder.parameters;

import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;


public interface QueryParameter
        extends ObjectifyQueryRequestLimitedConfigurer {

	public String getParameterString();

}
