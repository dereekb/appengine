package com.dereekb.gae.server.datastore.objectify.query.builder;

import com.dereekb.gae.utilities.query.builder.parameters.ConfigurableQueryParameters;

/**
 * {@link ConfigurableQueryParameters} extension that implements
 * {@link ObjectifyQueryRequestLimitedConfigurer}.
 * 
 * @author dereekb
 *
 */
public interface ConfigurableObjectifyQueryRequestConfigurer
        extends ConfigurableQueryParameters, ObjectifyQueryRequestLimitedConfigurer {

}
