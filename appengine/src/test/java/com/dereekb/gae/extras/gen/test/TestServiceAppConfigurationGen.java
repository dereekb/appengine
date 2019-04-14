package com.dereekb.gae.extras.gen.test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.impl.AppServiceConfigurationInfoImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.model.local.impl.LocalModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppModelKeyEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppServicesConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.LoginServerAppServerInitializationConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.impl.LocalAppLoginTokenSecurityConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteAppWebHookEventServiceConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteServiceConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.event.WebHookEventSubmitterImplEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractWebServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.LoginGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.remote.RemoteEventServiceConfigurationGen;
import com.dereekb.gae.extras.gen.test.app.gae.local.TestModelGroupConfigurationGen;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthClientConfigImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * Used for generating test configuration for the project.
 *
 * @author dereekb
 *
 */
public class TestServiceAppConfigurationGen extends AbstractWebServiceAppConfigurationGen {

	@Override
	public AppConfiguration makeAppSpringConfiguration() {

		String appProjectId = "gae-test";
		String appProjectService = "test";
		String appProjectVersion = "v1";

		// Models
		// Login
		LocalModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		LocalModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		// Foo
		LocalModelConfigurationGroupImpl fooGroup = TestModelGroupConfigurationGen.makeLocalTestGroupConfig();

		List<LocalModelConfigurationGroup> modelConfigurations = ListUtility
		        .toList(loginGroup, appGroup, fooGroup);

		AppServiceConfigurationInfo appServiceConfigurationInfo = new AppServiceConfigurationInfoImpl(appProjectId,
		        appProjectService, appProjectVersion);

		// Services
		RemoteEventServiceConfigurationGen remoteEventServiceGen = new RemoteEventServiceConfigurationGen(appProjectId);
		RemoteServiceConfigurationImpl remoteEventService = remoteEventServiceGen.make();

		// Configuration
		AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer = new LocalAppLoginTokenSecurityConfigurerImpl();
		AppEventServiceListenersConfigurer appEventServiceListenersConfigurer = new WebHookEventSubmitterImplEventListenerConfigurer();
		AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer = new RemoteAppWebHookEventServiceConfigurerImpl(
		        remoteEventService);

		AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer = new AppModelKeyEventListenerConfigurer() {

			@Override
			public Map<String, String> configureModelKeyEventListenerEntries(AppConfiguration appConfiguration,
			                                                                 SpringBeansXMLBuilder builder) {
				return Collections.emptyMap();
			}

		};

		AppServicesConfigurerImpl appServicesConfigurer = new AppServicesConfigurerImpl(appLoginTokenSecurityConfigurer,
		        appEventServiceListenersConfigurer, appWebHookEventServiceConfigurer,
		        appModelKeyEventListenerConfigurer);

		appServicesConfigurer
		        .setAppServerInitializationConfigurer(new LoginServerAppServerInitializationConfigurerImpl());

		AppConfigurationImpl configuration = new AppConfigurationImpl(appServiceConfigurationInfo,
		        appServicesConfigurer, modelConfigurations);

		configuration.setAppName("GAE Core Test App");

		configuration.setIsLoginServer(true);
		configuration.setAppId(1L);

		configuration.setRemoteServices(remoteEventService);

		LoginTokenAppSecurityBeansConfigurerImpl appSecurityBeansConfigurer = new LoginTokenAppSecurityBeansConfigurerImpl();

		OAuthClientConfig facebookOAuthConfig = new OAuthClientConfigImpl("431391914300748", "102a10dd9bfa5e2783e57a2f09b0c2ac");
		appSecurityBeansConfigurer.setFacebookOAuthConfig(facebookOAuthConfig);

		// TODO: Add Google

		configuration.setAppSecurityBeansConfigurer(appSecurityBeansConfigurer);

		return configuration;
	}

}
