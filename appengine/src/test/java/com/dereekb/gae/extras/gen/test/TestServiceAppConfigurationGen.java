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
import com.dereekb.gae.extras.gen.app.config.app.services.AppFirebaseServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppMailServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppModelKeyEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppFirebaseServiceConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.AppServicesConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.LoginServerAppServerInitializationConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.impl.MailgunAppMailServiceConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.LoginTokenAppSecurityBeansConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.impl.LocalAppLoginTokenSecurityConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.local.impl.NoopAppWebHookEventServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteServiceConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.event.NoopAppEventListenerConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.service.AbstractWebServiceAppConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.local.LoginGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.remote.RemoteEventServiceConfigurationGen;
import com.dereekb.gae.extras.gen.test.app.gae.local.TestModelGroupConfigurationGen;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.mail.service.MailUser;
import com.dereekb.gae.server.mail.service.impl.MailUserImpl;
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
		String appApiVersion = "v1";

		String appName = "GAE Core Test App";
		String developmentProxy = "http://gae-nginx:8080";	// Unused
		String developmentHost = "localhost:8080";

		// TODO: Add to configuration.
		String firebaseDatabaseUrl = TestStaticServerConfiguration.FIREBASE_DATABASE_URL;
		String developmentFirebaseDatabaseUrl = TestStaticServerConfiguration.FIREBASE_DATABASE_URL;

		Long appId = 1L;

		MailUser systemMailUser =  new MailUserImpl("test@gae.dereekb.com", "Test Server");

		// Models
		// Login
		LocalModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		LocalModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		// Foo
		LocalModelConfigurationGroupImpl fooGroup = TestModelGroupConfigurationGen.makeLocalTestGroupConfig();

		List<LocalModelConfigurationGroup> modelConfigurations = ListUtility.toList(loginGroup, appGroup, fooGroup);

		AppServiceConfigurationInfo appServiceConfigurationInfo = new AppServiceConfigurationInfoImpl(appProjectId,
		        appProjectService, appProjectVersion);

		// Services
		RemoteEventServiceConfigurationGen remoteEventServiceGen = new RemoteEventServiceConfigurationGen(appProjectId, appProjectVersion, appApiVersion);
		RemoteServiceConfigurationImpl remoteEventService = remoteEventServiceGen.make();

		// Configuration
		AppServerInitializationConfigurer appServerInitializationConfigurer = new LoginServerAppServerInitializationConfigurerImpl();
		AppLoginTokenSecurityConfigurer appLoginTokenSecurityConfigurer = new LocalAppLoginTokenSecurityConfigurerImpl();
		AppEventServiceListenersConfigurer appEventServiceListenersConfigurer = new NoopAppEventListenerConfigurer(); // new
		                                                                                                              // WebHookEventSubmitterImplEventListenerConfigurer();
		AppWebHookEventServiceConfigurer appWebHookEventServiceConfigurer = new NoopAppWebHookEventServiceConfigurer(); // new
		                                                                                                                // LocalAppWebHookEventServiceConfigurer();
		                                                                                                                // //
		                                                                                                                // =
		                                                                                                                // new
		                                                                                                                // RemoteAppWebHookEventServiceConfigurerImpl(remoteEventService);

		AppModelKeyEventListenerConfigurer appModelKeyEventListenerConfigurer = new AppModelKeyEventListenerConfigurer() {

			@Override
			public Map<String, String> configureModelKeyEventListenerEntries(AppConfiguration appConfiguration,
			                                                                 SpringBeansXMLBuilder builder) {
				return Collections.emptyMap();
			}

		};

		AppMailServiceConfigurer appMailServiceConfigurer = new MailgunAppMailServiceConfigurerImpl(systemMailUser);

		AppServicesConfigurerImpl appServicesConfigurer = new AppServicesConfigurerImpl(
		        appServerInitializationConfigurer, appLoginTokenSecurityConfigurer, appEventServiceListenersConfigurer,
		        appWebHookEventServiceConfigurer, appModelKeyEventListenerConfigurer, appMailServiceConfigurer);

		AppFirebaseServiceConfigurer firebaseServiceConfigurer = new AppFirebaseServiceConfigurerImpl(firebaseDatabaseUrl, developmentFirebaseDatabaseUrl);
		appServicesConfigurer.setAppFirebaseServiceConfigurer(firebaseServiceConfigurer);

		AppConfigurationImpl configuration = new AppConfigurationImpl(appServiceConfigurationInfo,
		        appServicesConfigurer, modelConfigurations);

		configuration.setAppName(appName);
		configuration.setAppTaskQueueName(appProjectService);
		configuration.setAppId(appId);

		configuration.setAppDevelopmentProxyUrl(developmentProxy);
		configuration.setAppDevelopmentServerHostUrl(developmentHost);

		configuration.setIsRootServer(true);
		configuration.setIsLoginServer(true);
		configuration.setRemoteServices(remoteEventService);

		LoginTokenAppSecurityBeansConfigurerImpl appSecurityBeansConfigurer = new LoginTokenAppSecurityBeansConfigurerImpl();

		appSecurityBeansConfigurer.setFacebookOAuthConfig(TestStaticServerConfiguration.TEST_FACEBOOK_OAUTH_CONFIG);

		// TODO: Add Google configuration.

		configuration.setAppSecurityBeansConfigurer(appSecurityBeansConfigurer);

		return configuration;
	}

}
