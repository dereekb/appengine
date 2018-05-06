package com.gae.server.service.login.extras.gen.app;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.impl.AppServiceConfigurationInfoImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServicesConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppServicesConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.impl.LocalAppLoginTokenSecurityConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteAppWebHookEventServiceConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteServiceConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.LoginGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.remote.RemoteEventServiceConfigurationGen;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class LoginServiceAppConfigurationGen extends AbstractServiceAppConfigurationGen {

	@Override
	public AppConfiguration makeAppConfiguration() {

		String appProjectId = "gae-test-server";

		// Models
		// Login
		LocalModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		LocalModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<LocalModelConfigurationGroup> modelConfigurations = ListUtility
		        .toList((LocalModelConfigurationGroup) loginGroup, appGroup);

		AppServiceConfigurationInfo appServiceConfigurationInfo = new AppServiceConfigurationInfoImpl(appProjectId,
		        "login", "v1");

		// Services
		RemoteEventServiceConfigurationGen remoteEventServiceGen = new RemoteEventServiceConfigurationGen(appProjectId);
		RemoteServiceConfigurationImpl remoteEventService = remoteEventServiceGen.make();

		// Configuration
		AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer = new LocalAppLoginTokenSecurityConfigurerImpl();
		AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer = new RemoteAppWebHookEventServiceConfigurerImpl(
		        remoteEventService);
		AppEventListenerConfigurer appEventListenerConfigurer = new AppEventListenerConfigurer() {

			@Override
			public Map<String, String> configureEventListenerEntries(AppConfiguration appConfiguration,
			                                                         SpringBeansXMLBuilder builder) {
				return Collections.emptyMap();
			}

		};

		AppServicesConfigurer appServicesConfigurer = new AppServicesConfigurerImpl(appLoginTokenSecurityConfigurer,
		        appWebHookEventServiceConfigurer, appEventListenerConfigurer);

		AppConfigurationImpl configuration = new AppConfigurationImpl(appServiceConfigurationInfo,
		        appServicesConfigurer, modelConfigurations);

		configuration.setAppName("GAE Test Login Service");

		configuration.setAppTaskQueueName("login");
		configuration.setAppId(1L);

		configuration.setRemoteServices(remoteEventService);

		return configuration;
	}

}
