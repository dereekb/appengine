package com.dereekb.gae.server.auth.model.pointer.search.query;

import com.dereekb.gae.server.auth.model.login.Login;
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
 * {@link LoginPointerQuery}.
 *
 * @author dereekb
 *
 */
public class LoginPointerQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl {

	private static final ObjectifyKeyFieldParameterBuilder<Login> LOGIN_BUILDER = new ObjectifyKeyFieldParameterBuilder<Login>(
	        ModelKeyType.NUMBER, Login.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyLoginPointerQuery();
	}

	public static class ObjectifyLoginPointerQuery extends LoginPointerQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public void setLogin(Key<Login> login) {
			this.setLogin(LOGIN_BUILDER.getUtil().toModelKey(login));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			LOGIN_BUILDER.configure(request, this.getLogin());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
		}

	}

}
