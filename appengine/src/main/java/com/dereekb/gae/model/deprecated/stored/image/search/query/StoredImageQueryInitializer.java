package com.dereekb.gae.model.stored.image.search.query;

import java.util.Map;

import com.dereekb.gae.model.deprecated.stored.image.search.query.StoredImageQueryInitializer.ObjectifyStoredImageQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link StoredImageQuery}.
 *
 * @author dereekb
 *
 */
public class StoredImageQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyStoredImageQuery> {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyStoredImageQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyStoredImageQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyStoredImageQuery(parameters);
	}

	public static class ObjectifyStoredImageQuery extends StoredImageQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyStoredImageQuery() {
			super();
		}

		public ObjectifyStoredImageQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
		}

	}

}
