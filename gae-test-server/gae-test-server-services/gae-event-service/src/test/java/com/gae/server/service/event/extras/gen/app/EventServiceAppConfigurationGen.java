package com.gae.server.service.event.extras.gen.app;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.impl.AppServiceConfigurationInfoImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppModelKeyEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppEventServiceListenersConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppServicesConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteAppLoginTokenSecurityConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteAppWebHookEventServiceConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteServiceConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteSystemLoginTokenFactoryConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.remote.RemoteLoginServiceConfigurationGen;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class EventServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	@Override
	public AppConfiguration makeAppConfiguration() {

		String appProjectId = "gae-test-server";
		String appProjectService = "event";
		String appProjectVersion = "v1";

		// Models
		// TODO: Add Notification Models

		List<LocalModelConfigurationGroup> modelConfigurations = ListUtility.toList();

		AppServiceConfigurationInfo appServiceConfigurationInfo = new AppServiceConfigurationInfoImpl(appProjectId,
		        appProjectService, appProjectVersion);

		// Services
		RemoteLoginServiceConfigurationGen remoteLoginServiceGen = new RemoteLoginServiceConfigurationGen(appProjectId);
		RemoteServiceConfigurationImpl remoteLoginService = remoteLoginServiceGen.make();

		// Configuration
		AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer = new RemoteAppLoginTokenSecurityConfigurerImpl(remoteLoginService);
		AppEventServiceListenersConfigurer appEventServiceListenersConfigurer = new AppEventServiceListenersConfigurerImpl();
		AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer = new RemoteAppWebHookEventServiceConfigurerImpl(
		        remoteLoginService);
		AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer = new AppModelKeyEventListenerConfigurer() {

			@Override
			public Map<String, String> configureModelKeyEventListenerEntries(AppConfiguration appConfiguration,
			                                                                 SpringBeansXMLBuilder builder) {
				return Collections.emptyMap();
			}

		};

		AppServicesConfigurer appServicesConfigurer = new AppServicesConfigurerImpl(appLoginTokenSecurityConfigurer,
		        appEventServiceListenersConfigurer, appWebHookEventServiceConfigurer,
		        appModelKeyEventListenerConfigurer);

		AppConfigurationImpl configuration = new AppConfigurationImpl(appServiceConfigurationInfo,
		        appServicesConfigurer, modelConfigurations);

		RemoteSystemLoginTokenFactoryConfigurerImpl remoteSystemLoginTokenFactory = new RemoteSystemLoginTokenFactoryConfigurerImpl(
		        remoteLoginService);
		LoginTokenAppSecurityBeansConfigurerImpl securityBeansConfigurer = new LoginTokenAppSecurityBeansConfigurerImpl(
		        remoteSystemLoginTokenFactory);

		configuration.setAppName("GAE Test Login Service");

		configuration.setAppTaskQueueName("login");
		configuration.setAppId(1L);
		configuration.setAppSecurityBeansConfigurer(securityBeansConfigurer);
		configuration.setAppSecret("SECRET");

		configuration.setIsLoginServer(false);
		configuration.setRemoteServices(remoteLoginService);

		return configuration;
	}

	/*
	 * @Override
	 * public AppConfiguration makeAppConfiguration() {
	 *
	 * // TODO: Add model configurations.
	 *
	 * List<AppModelConfigurationGroup> modelConfigurations =
	 * ListUtility.toList();
	 *
	 * AppConfigurationImpl configuration = new
	 * AppConfigurationImpl(modelConfigurations);
	 *
	 * configuration.setAppName("gae-event-service");
	 * configuration.setAppVersion("v1");
	 * configuration.setAppServiceName("event");
	 * configuration.setAppTaskQueueName("event");
	 * configuration.setAppId(1L);
	 *
	 * return configuration;
	 * }
	 */

}
