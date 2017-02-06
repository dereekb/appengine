package com.dereekb.gae.server.auth.model.login.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.search.query.LoginQueryInitializer.ObjectifyLoginQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link LoginQuery}.
 *
 * @author dereekb
 *
 */
public class LoginQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyLoginQuery> {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyLoginQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyLoginQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyLoginQuery(parameters);
	}

	public static class ObjectifyLoginQuery extends LoginQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyLoginQuery() {
			super();
		}

		public ObjectifyLoginQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getDate());
		}

	}

}
