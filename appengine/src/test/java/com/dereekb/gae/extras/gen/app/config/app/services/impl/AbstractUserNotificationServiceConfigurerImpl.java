package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppUserNotificationServiceConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.notification.service.PushNotificationService;
import com.dereekb.gae.server.notification.service.impl.test.TestPushNotificationServiceImpl;
import com.dereekb.gae.server.notification.user.service.impl.NotificationSettingsTokenServiceImpl;
import com.dereekb.gae.server.notification.user.service.impl.UserPushNotificationServiceImpl;
import com.dereekb.gae.utilities.misc.env.EnvStringUtility;

/**
 * {@link AppUserNotificationServiceConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractUserNotificationServiceConfigurerImpl
        implements AppUserNotificationServiceConfigurer {

	private boolean enableOnlyInProduction = false;

	/**
	 * Whether or not to use the normal push notification service in a
	 * production environment.
	 * <p>
	 * If true, a {@link TestPushNotificationServiceImpl} will be used in a
	 * development environment.
	 *
	 * @return
	 */
	public boolean isEnableOnlyInProduction() {
		return this.enableOnlyInProduction;
	}

	public void setEnableOnlyInProduction(boolean enableOnlyInProduction) {
		this.enableOnlyInProduction = enableOnlyInProduction;
	}

	// MARK: AppUserNotificationServiceConfigurer
	@Override
	public void configureUserNotificationService(AppConfiguration appConfiguration,
	                                             SpringBeansXMLBuilder builder) {

		boolean makeWrappedPushNotificationBean = !EnvStringUtility.isProduction();

		String pushNotificationBeanId = appConfiguration.getAppBeans().getPushNotificationServiceBeanId();
		String userPushNotificationBeanId = appConfiguration.getAppBeans().getUserPushNotificationServiceBeanId();

		String primaryPushNotificationBeanId = pushNotificationBeanId;

		if (makeWrappedPushNotificationBean) {
			primaryPushNotificationBeanId = "t_" + pushNotificationBeanId;
		}

		builder.comment("User Push Notification Service");

		String notificationSettingsTokenServiceBeanId = "notificationSettingsTokenService";
		builder.bean(userPushNotificationBeanId).beanClass(UserPushNotificationServiceImpl.class).c()
		        .ref(primaryPushNotificationBeanId).ref(notificationSettingsTokenServiceBeanId);

		builder.bean(notificationSettingsTokenServiceBeanId).beanClass(NotificationSettingsTokenServiceImpl.class).c()
		        .ref("notificationSettingsRegistry");

		builder.comment("Push Notification Service");

		if (makeWrappedPushNotificationBean) {
			builder.bean(primaryPushNotificationBeanId).beanClass(TestPushNotificationServiceImpl.class)
			        .factoryMethod("makeForEnvironment").c().ref(pushNotificationBeanId);
		}

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
