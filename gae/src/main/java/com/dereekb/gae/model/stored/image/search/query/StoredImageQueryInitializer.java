package com.dereekb.gae.model.stored.image.search.query;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link StoredImageQuery}.
 *
 * @author dereekb
 *
 */
public class StoredImageQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyStoredImageQuery();
	}

	public static class ObjectifyStoredImageQuery extends StoredImageQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
		}

	}

}
