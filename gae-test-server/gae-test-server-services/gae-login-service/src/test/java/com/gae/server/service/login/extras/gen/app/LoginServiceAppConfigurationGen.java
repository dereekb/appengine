package com.gae.server.service.login.extras.gen.app;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.AppConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.test.TestConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.gae.AppGroupConfigurationGen;
import com.dereekb.gae.extras.gen.app.gae.LoginGroupConfigurationGen;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class LoginServiceAppConfigurationGen {

	private static final String APP_SPRING_PATH = "src/main/webapp/WEB-INF";
	private static final String APP_TEST_SPRING_PATH = "src/test/webapp";

	public static AppConfiguration makeAppConfiguration() {

		// Login
		AppModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		AppModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility
		        .toList((AppModelConfigurationGroup) loginGroup, appGroup);

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
	public void testMakeAppConfiguration() throws IOException {

		AppConfigurationGenerator generator = new AppConfigurationGenerator(APP_CONFIG);
		generator.setAppFolderName("spring");

		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl(APP_SPRING_PATH);
		writer.writeFiles(folder);
	}

	@Test
	public void testMakeTestClentConfiguration() throws IOException {

		TestConfigurationGenerator generator = new TestConfigurationGenerator(APP_CONFIG);
		GenFolderImpl folder = generator.generateConfigurations();

		folder.setFolderName("spring");

		GenFilesWriterImpl writer = new GenFilesWriterImpl(APP_TEST_SPRING_PATH);
		writer.writeFiles(folder);
	}

}
