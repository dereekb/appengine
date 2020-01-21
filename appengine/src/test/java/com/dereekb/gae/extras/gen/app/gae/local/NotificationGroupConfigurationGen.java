package com.dereekb.gae.extras.gen.app.gae.local;

import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationImpl;
import com.dereekb.gae.server.notification.model.token.NotificationSettings;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Local notification group generation.
 * <p>
 * Contains models such as {@link NotificationSettings}.
 *
 * @author dereekb
 *
 */
public class NotificationGroupConfigurationGen {

	public static final String NOTIFICATION_GROUP = "notification";

	public static LocalModelConfigurationGroupImpl makeInternalLocalNotificationGroupConfig() {

		// Notification
		LocalModelConfigurationImpl notificationSettingsModel = makeNotificationSettingsModelConfig();

		LocalModelConfigurationGroupImpl notificationGroup = new LocalModelConfigurationGroupImpl(NOTIFICATION_GROUP,
		        ListUtility.toList(notificationSettingsModel));

		return notificationGroup;
	}

	public static LocalModelConfigurationImpl makeNotificationSettingsModelConfig() {
		LocalModelConfigurationImpl readOnlyAppModel = new LocalModelConfigurationImpl(NotificationSettings.class);

		readOnlyAppModel.setInternalModelOnly(true);
		readOnlyAppModel.setSystemModelOnly(true);
		readOnlyAppModel.setIsReadOnly();

		return readOnlyAppModel;
	}

}
