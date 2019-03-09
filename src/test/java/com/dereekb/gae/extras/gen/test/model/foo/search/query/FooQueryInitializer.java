package com.dereekb.gae.extras.gen.test.model.foo.search.query;

import java.util.Map;

import com.dereekb.gae.extras.gen.test.model.foo.search.query.FooQueryInitializer.ObjectifyFooQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link FooQuery}.
 *
 * @author dereekb
 *
 */
public class FooQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyFooQuery> {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyFooQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyFooQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyFooQuery(parameters);
	}

	public static class ObjectifyFooQuery extends FooQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyFooQuery() {
			super();
		}

		public ObjectifyFooQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {

			// TODO

			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getNumber());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
		}

	}

}
