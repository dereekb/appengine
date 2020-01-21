package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.notification.service.impl.firebase.FirebasePushNotificationServiceImpl;

/**
 * {@link AbstractUserNotificationServiceConfigurerImpl} for Firebase that uses
 * a {@link FirebasePushNotificationServiceImpl}.
 *
 * @author dereekb
 *
 */
public class FirebaseUserNotificationServiceConfigurerImpl extends AbstractUserNotificationServiceConfigurerImpl {

	@Override
	protected void configurePushNotificationService(AppConfiguration appConfiguration,
	                                                SpringBeansXMLBuilder builder,
	                                                String pushNotificationBeanId) {

		builder.bean(pushNotificationBeanId).beanClass(FirebasePushNotificationServiceImpl.class).c()
		        .ref(appConfiguration.getAppBeans().getFirebaseServiceBeanId());
	}

}
