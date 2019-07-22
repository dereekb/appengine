package com.dereekb.gae.server.app.model.app.search.query;

import java.util.Map;

import com.dereekb.gae.server.app.model.app.search.query.AppQueryInitializer.ObjectifyAppQuery;
import com.dereekb.gae.server.auth.model.login.Login;
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
 * {@link AppQuery}.
 *
 * @author dereekb
 *
 */
public class AppQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyAppQuery> {

	private static final ObjectifyKeyFieldParameterBuilder<Login> LOGIN_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, Login.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyAppQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyAppQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyAppQuery(parameters);
	}

	public static class ObjectifyAppQuery extends AppQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyAppQuery() {
			super();
		}

		public ObjectifyAppQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		public void setLogin(Key<Login> login) {
			this.setLogin(LOGIN_BUILDER.getUtil().toModelKey(login));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			LOGIN_BUILDER.configure(request, this.getLogin());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getOwnerId());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getLevel());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getSystemKey());
		}

	}

}
