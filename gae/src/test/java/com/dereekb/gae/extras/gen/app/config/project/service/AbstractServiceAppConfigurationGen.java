package com.dereekb.gae.extras.gen.app.config.project.service;

import java.io.IOException;

import org.junit.Test;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.test.TestConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;

public abstract class AbstractServiceAppConfigurationGen {

	public static final String DEFAULT_APP_SPRING_PATH = "src/main/webapp/WEB-INF";
	public static final String DEFAULT_APP_TEST_SPRING_PATH = "src/test/webapp";

	private String appSpringPath = DEFAULT_APP_SPRING_PATH;
	private String testSpringPath = DEFAULT_APP_TEST_SPRING_PATH;

	private String appFolderName = "spring";
	private String testFolderName = "spring";

	@Test
	public void makeConfigurations() throws IOException {
		AppConfiguration configuration = this.makeAppConfiguration();
		this.makeAppConfiguration(configuration);
	}

	public abstract AppConfiguration makeAppConfiguration();

	public void makeAppConfiguration(AppConfiguration appConfig) throws IOException {

		AppConfigurationGenerator generator = new AppConfigurationGenerator(appConfig);
		generator.setAppFolderName(this.appFolderName);

		GenFolder folder = generator.generateConfigurations();

		GenFilesWriterImpl writer = new GenFilesWriterImpl(this.appSpringPath);
		writer.writeFiles(folder);
	}

	public void makeTestClentConfiguration(AppConfiguration appConfig) throws IOException {

		TestConfigurationGenerator generator = new TestConfigurationGenerator(appConfig);
		GenFolderImpl folder = generator.generateConfigurations();

		folder.setFolderName(this.testFolderName);

		GenFilesWriterImpl writer = new GenFilesWriterImpl(this.testSpringPath);
		writer.writeFiles(folder);
	}

}
