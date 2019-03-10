package com.dereekb.gae.extras.gen.app.config.project.service;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.test.TestConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFilesWriter;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;

public abstract class AbstractServiceAppConfigurationGen {

	public static final String DEFAULT_APP_WEB_INF_PATH = "src/main/webapp/WEB-INF";
	public static final String DEFAULT_APP_TEST_SPRING_PATH = "src/test/webapp";

	private String appWebInfPath = DEFAULT_APP_WEB_INF_PATH;
	private String testSpringPath = DEFAULT_APP_TEST_SPRING_PATH;

	private String appSpringFolderName = "spring";
	private String testSpringFolderName = "spring";

	// MARK: Configurations
	@Test
	public final void makeConfigurations() throws IOException {
		AppConfiguration configuration = this.makeAppSpringConfiguration();
		this.generateWebInfConfigurations(configuration);
		this.makeTestClentConfiguration(configuration);
	}

	private final void generateWebInfConfigurations(AppConfiguration appConfig) throws IOException {
		GenFilesWriterImpl writer = new GenFilesWriterImpl(this.appWebInfPath);
		this.writeWebInfConfigurations(appConfig, writer);
	}

	protected void writeWebInfConfigurations(AppConfiguration appConfig, GenFilesWriter writer) throws IOException {
		this.writeAppSpringFolder(appConfig, writer);
	}

	// MARK: Spring
	public void writeAppSpringFolder(AppConfiguration appConfig, GenFilesWriter writer) throws IOException {

		AppConfigurationGenerator generator = new AppConfigurationGenerator(appConfig);
		generator.setAppFolderName(this.appSpringFolderName);

		GenFolder springFolder = generator.generateConfigurations();
		writer.writeFiles(springFolder);
	}

	public abstract AppConfiguration makeAppSpringConfiguration();

	// MARK: Test
	public void makeTestClentConfiguration(AppConfiguration appConfig) throws IOException {

		TestConfigurationGenerator generator = new TestConfigurationGenerator(appConfig);
		GenFolderImpl folder = generator.generateConfigurations();

		folder.setFolderName(this.testSpringFolderName);

		GenFilesWriterImpl writer = new GenFilesWriterImpl(this.testSpringPath);
		writer.writeFiles(folder);
	}

}
