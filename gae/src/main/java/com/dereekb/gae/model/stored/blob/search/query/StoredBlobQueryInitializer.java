package com.dereekb.gae.model.stored.blob.search.query;

import java.util.Map;

import com.dereekb.gae.model.stored.blob.search.query.StoredBlobQueryInitializer.ObjectifyStoredBlobQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link StoredBlobQuery}.
 *
 * @author dereekb
 *
 */
public class StoredBlobQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyStoredBlobQuery> {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyStoredBlobQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyStoredBlobQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyStoredBlobQuery(parameters);
	}

	public static class ObjectifyStoredBlobQuery extends StoredBlobQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyStoredBlobQuery() {
			super();
		}

		public ObjectifyStoredBlobQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getDate());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
		}

	}

}
