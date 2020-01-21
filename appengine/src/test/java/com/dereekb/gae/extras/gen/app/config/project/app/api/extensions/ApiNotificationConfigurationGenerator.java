package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppUserNotificationServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.server.notification.ApiUserNotificationServiceController;

public class ApiNotificationConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String NOTIFICATION_FILE_NAME = "notification";

	public ApiNotificationConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(NOTIFICATION_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		AppUserNotificationServiceConfigurer serviceConfigurer = this.getAppConfig().getAppServicesConfigurer()
		        .getAppUserNotificationServiceConfigurer();

		if (serviceConfigurer == null) {
			builder.comment("User Notifications are not configured for this app.");
		} else {
			builder.comment("User Notifications");
			builder.bean("apiUserNotificationServiceController").beanClass(ApiUserNotificationServiceController.class)
			        .c().ref(this.getAppConfig().getAppBeans().getUserPushNotificationServiceBeanId());
		}

		return builder;
	}

}
