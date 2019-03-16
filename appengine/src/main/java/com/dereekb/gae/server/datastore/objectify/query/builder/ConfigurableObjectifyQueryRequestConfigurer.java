package com.dereekb.gae.server.datastore.objectify.query.builder;

import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableEncodedQueryParameters;

/**
 * {@link ConfigurableEncodedQueryParameters} extension that implements
 * {@link ObjectifyQueryRequestLimitedConfigurer}.
 * 
 * @author dereekb
 *
 */
public interface ConfigurableObjectifyQueryRequestConfigurer
        extends ConfigurableEncodedQueryParameters, ObjectifyQueryRequestLimitedConfigurer {

}
