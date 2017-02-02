package com.dereekb.gae.model.stored.blob.search.query;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link StoredBlobQuery}.
 *
 * @author dereekb
 *
 */
public class StoredBlobQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyStoredBlobQuery();
	}

	public static class ObjectifyStoredBlobQuery extends StoredBlobQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getDate());
		}

	}

}
