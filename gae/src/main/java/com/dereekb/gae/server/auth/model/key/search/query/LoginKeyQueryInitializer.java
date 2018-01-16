package com.dereekb.gae.server.auth.model.key.search.query;

import java.util.Map;

import com.dereekb.gae.server.auth.model.key.search.query.LoginKeyQueryInitializer.ObjectifyLoginKeyQuery;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
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
 * {@link LoginKeyQuery}.
 *
 * @author dereekb
 *
 */
public class LoginKeyQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyLoginKeyQuery> {

	private static final ObjectifyKeyFieldParameterBuilder<Login> LOGIN_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, Login.class);
	private static final ObjectifyKeyFieldParameterBuilder<LoginPointer> LOGIN_POINTER_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NAME, LoginPointer.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyLoginKeyQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyLoginKeyQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyLoginKeyQuery(parameters);
	}

	public static class ObjectifyLoginKeyQuery extends LoginKeyQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyLoginKeyQuery() {
			super();
		}

		public ObjectifyLoginKeyQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		public void setLogin(Key<Login> login) {
			this.setLogin(LOGIN_BUILDER.getUtil().toModelKey(login));
		}

		public void setLoginPointer(Key<LoginPointer> loginPointer) {
			this.setLoginPointer(LOGIN_POINTER_BUILDER.getUtil().toModelKey(loginPointer));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			LOGIN_BUILDER.configure(request, this.getLogin());
			LOGIN_POINTER_BUILDER.configure(request, this.getLoginPointer());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
		}

	}

}
