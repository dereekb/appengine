package com.dereekb.gae.extras.gen.app.gae;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.model.impl.AppModelConfigurationGroupImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.AppConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.ApiConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.context.ContextConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.taskqueue.TaskQueueConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.test.TestModelsConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;

public class GaeAppConfigurationGen {

	public static AppConfiguration makeGaeAppConfiguration() {

		// Login
		AppModelConfigurationGroupImpl loginGroup = LoginGroupConfigurationGen.makeLocalLoginGroupConfig();

		// App
		AppModelConfigurationGroupImpl appGroup = AppGroupConfigurationGen.makeLocalAppGroupConfig();

		List<AppModelConfigurationGroup> modelConfigurations = ListUtility.toList(loginGroup, appGroup);

		AppConfigurationImpl configuration = new AppConfigurationImpl(modelConfigurations);

		return configuration;
	}

	public static final AppConfiguration APP_CONFIG = makeGaeAppConfiguration();

	@Test
	public void testMakeTestClentConfiguration() throws IOException {

		TestModelsConfigurationGenerator generator = new TestModelsConfigurationGenerator(APP_CONFIG);
		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl();
		writer.writeFiles(folder);
	}

	@Ignore
	@Test
	public void testMakeApiConfiguration() throws IOException {

		ApiConfigurationGenerator generator = new ApiConfigurationGenerator(APP_CONFIG);
		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl();
		writer.writeFiles(folder);
	}

	@Ignore
	@Test
	public void testMakeContextConfiguration() throws IOException {

		ContextConfigurationGenerator generator = new ContextConfigurationGenerator(APP_CONFIG);
		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl();
		writer.writeFiles(folder);
	}

	@Ignore
	@Test
	public void testMakeTaskqueueConfiguration() throws IOException {

		TaskQueueConfigurationGenerator generator = new TaskQueueConfigurationGenerator(APP_CONFIG);
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
