package com.gae.server.service.login.extras.gen.app;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.AppConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.test.TestModelsConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.gae.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.LoginGroupConfigurationGen;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class LoginServiceAppConfigurationGen {

	public static AppConfiguration makeAppConfiguration() {

		// Login
		AppModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		AppModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility.toList((AppModelConfigurationGroup) loginGroup, appGroup);

		AppConfigurationImpl configuration = new AppConfigurationImpl(modelConfigurations);

		configuration.setAppName("gae-test-login");
		configuration.setAppVersion("v1");
		configuration.setAppServiceName("login");
		configuration.setAppTaskQueueName("login");
		configuration.setAppId(1L);

		return configuration;
	}

	public static final AppConfiguration APP_CONFIG = makeAppConfiguration();

	@Test
	public void testMakeTestClentConfiguration() throws IOException {

		TestModelsConfigurationGenerator generator = new TestModelsConfigurationGenerator(APP_CONFIG);
		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl();
		writer.writeFiles(folder);
	}

	@Test
	public void testMakeAppConfiguration() throws IOException {

		AppConfigurationGenerator generator = new AppConfigurationGenerator(APP_CONFIG);
		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl();
		writer.writeFiles(folder);
	}

}
