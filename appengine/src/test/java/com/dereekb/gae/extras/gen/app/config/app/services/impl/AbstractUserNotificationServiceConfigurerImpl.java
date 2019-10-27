package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppUserNotificationServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.notification.service.PushNotificationService;
import com.dereekb.gae.server.notification.user.service.impl.NotificationSettingsTokenServiceImpl;
import com.dereekb.gae.server.notification.user.service.impl.UserPushNotificationServiceImpl;

/**
 * {@link AppUserNotificationServiceConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractUserNotificationServiceConfigurerImpl
        implements AppUserNotificationServiceConfigurer {

	// MARK: AppUserNotificationServiceConfigurer
	@Override
	public void configureUserNotificationService(AppConfiguration appConfiguration,
	                                             SpringBeansXMLBuilder builder) {

		String pushNotificationBeanId = appConfiguration.getAppBeans().getPushNotificationServiceBeanId();
		String userPushNotificationBeanId = appConfiguration.getAppBeans().getUserPushNotificationServiceBeanId();

		builder.comment("User Push Notification Service");

		String notificationSettingsTokenServiceBeanId = "notificationSettingsTokenService";
		builder.bean(userPushNotificationBeanId).beanClass(UserPushNotificationServiceImpl.class).c()
		        .ref(pushNotificationBeanId).ref(notificationSettingsTokenServiceBeanId);

		builder.bean(notificationSettingsTokenServiceBeanId).beanClass(NotificationSettingsTokenServiceImpl.class).c()
		        .ref("notificationSettingsRegistry");

		builder.comment("Push Notification Service");
		this.configurePushNotificationService(appConfiguration, builder, pushNotificationBeanId);
	}

	/**
	 * Configures the {@link PushNotificationService} bean.
	 *
	 * @param appConfiguration
	 * @param builder
	 */
	protected abstract void configurePushNotificationService(AppConfiguration appConfiguration,
	                                                         SpringBeansXMLBuilder builder,
	                                                         String pushNotificationBeanId);

}
