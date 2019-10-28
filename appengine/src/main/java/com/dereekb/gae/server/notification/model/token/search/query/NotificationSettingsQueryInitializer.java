package com.dereekb.gae.server.notification.model.token.search.query;

import java.util.Map;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilderInitializer;
import com.dereekb.gae.server.datastore.objectify.query.builder.AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl;
import com.dereekb.gae.server.datastore.objectify.query.builder.ConfigurableObjectifyQueryRequestConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryFactory;
import com.dereekb.gae.server.notification.model.token.search.query.NotificationSettingsQueryInitializer.ObjectifyNotificationSettingsQuery;

/**
 * {@link ObjectifyQueryRequestLimitedBuilderInitializer} implementation for a
 * {@link NotificationSettingsQuery}.
 *
 * @author dereekb
 *
 */
public class NotificationSettingsQueryInitializer extends AbstractObjectifyQueryRequestLimitedBuilderInitializerImpl
        implements ObjectifyQueryFactory<ObjectifyNotificationSettingsQuery> {

	@Override
	protected ConfigurableObjectifyQueryRequestConfigurer makeConfigurer() {
		return new ObjectifyNotificationSettingsQuery();
	}

	// MARK: ObjectifyQueryFactory
	@Override
	public ObjectifyNotificationSettingsQuery makeQuery(Map<String, String> parameters)
	        throws IllegalArgumentException {
		return new ObjectifyNotificationSettingsQuery(parameters);
	}

	public static class ObjectifyNotificationSettingsQuery extends NotificationSettingsQuery
	        implements ConfigurableObjectifyQueryRequestConfigurer {

		public ObjectifyNotificationSettingsQuery() {
			super();
		}

		public ObjectifyNotificationSettingsQuery(Map<String, String> parameters) throws IllegalArgumentException {
			super(parameters);
		}

		// MARK: ConfigurableObjectifyQueryRequestConfigurer
		@Override
		public void configure(ObjectifyQueryRequestLimitedBuilder request) {
			// No fields to filter on.
		}

	}

}
