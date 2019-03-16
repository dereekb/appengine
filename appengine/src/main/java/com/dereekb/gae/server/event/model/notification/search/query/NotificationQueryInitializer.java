package com.dereekb.gae.server.event.model.notification.search.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.datastore.objectify.query.builder.parameters.impl.ObjectifyAbstractQueryFieldParameter;
import com.dereekb.gae.server.event.model.notification.search.query.NotificationQueryInitializer.ObjectifyNotificationQuery;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link NotificationQuery}.
 *
 * @author dereekb
 *
 */
public class NotificationQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyNotificationQuery> {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyNotificationQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyNotificationQuery makeQuery(Map<String, String> parameters) throws IllegalArgumentException {
		return new ObjectifyNotificationQuery(parameters);
	}

	public static class ObjectifyNotificationQuery extends NotificationQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyNotificationQuery() {
			super();
		}

		public ObjectifyNotificationQuery(Map<String, String> parameters) throws IllegalArgumentException {
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
