package com.dereekb.gae.server.auth.model.key.search.query;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyKeyFieldParameterBuilder;
import com.googlecode.objectify.Key;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link LoginKeyQuery}.
 *
 * @author dereekb
 *
 */
public class LoginKeyQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	private static final ObjectifyKeyFieldParameterBuilder<LoginPointer> LOGIN_POINTER_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NAME, LoginPointer.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyLoginKeyQuery();
	}

	public static class ObjectifyLoginKeyQuery extends LoginKeyQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public void setLoginPointer(Key<LoginPointer> loginPointer) {
			this.setLoginPointer(LOGIN_POINTER_BUILDER.getUtil().toModelKey(loginPointer));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			LOGIN_POINTER_BUILDER.configure(request, this.getLoginPointer());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
		}

	}

}
