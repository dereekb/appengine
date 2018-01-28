package com.dereekb.gae.server.app.model.hook.search.query;

import java.util.Map;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.app.model.hook.search.query.AppHookQueryInitializer.ObjectifyAppHookQuery;
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
 * {@link AppHookQuery}.
 *
 * @author dereekb
 *
 */
public class AppHookQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyAppHookQuery> {

	private static final ObjectifyKeyFieldParameterBuilder<App> APP_BUILDER = ObjectifyKeyFieldParameterBuilder
	        .make(ModelKeyType.NUMBER, App.class);

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyAppHookQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyAppHookQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyAppHookQuery(parameters);
	}

	public static class ObjectifyAppHookQuery extends AppHookQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyAppHookQuery() {
			super();
		}

		public ObjectifyAppHookQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		public void setApp(Key<App> login) {
			this.setApp(APP_BUILDER.getUtil().toModelKey(login));
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			APP_BUILDER.configure(request, this.getApp());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getGroup());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getEvent());
			ObjectifyAbstractQueryFieldParameter.tryConfigure(request, this.getDate());
		}

	}

}
