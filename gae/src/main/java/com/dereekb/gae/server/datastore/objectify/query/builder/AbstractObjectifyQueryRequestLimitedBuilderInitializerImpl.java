package com.dereekb.gae.server.datastore.objectify.query.builder;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;

/**
 * Abstract {@link ObjectifyQueryRequestLimitedBuilderInitializer}
 * implementation.
 * 
 * @author dereekb
 *
 */
public abstract class AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryRequestLimitedBuilderInitializer {

	@Override
	public void initalizeBuilder(ObjectifyQueryRequestLimitedBuilder builder,
	                             Map<String, String> parameters) {

		ConfigurableObjectifyQueryRequestConfigurer configurer = this.makeConfigurer();

		if (parameters != null) {
			configurer.setParameters(parameters);
			configurer.configure(builder);
		}

	}

	protected abstract ConfigurableObjectifyQueryRequestConfigurer makeConfigurer();

}
