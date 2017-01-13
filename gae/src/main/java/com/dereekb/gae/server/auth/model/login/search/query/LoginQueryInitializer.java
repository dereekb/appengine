package com.dereekb.gae.server.auth.model.login.search.query;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link LoginQuery}.
 *
 * @author dereekb
 *
 */
public class LoginQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyLoginQuery();
	}

	public static class ObjectifyLoginQuery extends LoginQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getDate());
		}

	}

}
