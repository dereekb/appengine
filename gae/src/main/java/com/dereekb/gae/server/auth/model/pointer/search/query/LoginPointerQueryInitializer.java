package com.dereekb.gae.server.auth.model.pointer.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.search.query.LoginPointerQueryInitializer.ObjectifyLoginPointerQuery;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
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
public class LoginPointerQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyLoginPointerQuery> {

	private static final ObjectifyKeyFieldParameterBuilder<Login> LOGIN_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, Login.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyLoginPointerQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyLoginPointerQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyLoginPointerQuery(parameters);
	}

	public static class ObjectifyLoginPointerQuery extends LoginPointerQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyLoginPointerQuery() {
			super();
		}

		public ObjectifyLoginPointerQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		public void setLogin(Login login) throws NullPointerException {
			this.setLogin(login.getObjectifyKey());
		}

		public void setLogin(Key<Login> login) {
			this.setLogin(LOGIN_BUILDER.getUtil().toModelKey(login));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			LOGIN_BUILDER.configure(request, this.getLogin());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getType());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
		}

	}

}
