package com.dereekb.gae.extras.gen.app.config.project.service;

import java.io.IOException;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.AppConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.test.TestConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFilesWriter;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFilesWriterImpl;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;

public abstract class AbstractServiceAppConfigurationGen {

	public static final String GENERATOR_JUNIT_TEST_FLAG = "appgen";

	public static final String DEFAULT_APP_WEB_INF_PATH = "src/main/webapp/WEB-INF";
	public static final String DEFAULT_APP_TEST_SPRING_PATH = "src/test/webapp";

	private boolean deleteSpringFolderBeforeGenerating = true;

	private String appWebInfPath = DEFAULT_APP_WEB_INF_PATH;
	private String testSpringPath = DEFAULT_APP_TEST_SPRING_PATH;

	private String appSpringFolderName = "spring";
	private String testSpringFolderName = "spring";

	public boolean isDeleteSpringFolderBeforeGenerating() {
		return this.deleteSpringFolderBeforeGenerating;
	}

	public void setDeleteSpringFolderBeforeGenerating(boolean deleteSpringFolderBeforeGenerating) {
		this.deleteSpringFolderBeforeGenerating = deleteSpringFolderBeforeGenerating;
	}

	public String getAppWebInfPath() {
		return this.appWebInfPath;
	}

	public void setAppWebInfPath(String appWebInfPath) {
		if (appWebInfPath == null) {
			throw new IllegalArgumentException("appWebInfPath cannot be null.");
		}

		this.appWebInfPath = appWebInfPath;
	}

	public String getTestSpringPath() {
		return this.testSpringPath;
	}

	public void setTestSpringPath(String testSpringPath) {
		if (testSpringPath == null) {
			throw new IllegalArgumentException("testSpringPath cannot be null.");
		}

		this.testSpringPath = testSpringPath;
	}

	public String getAppSpringFolderName() {
		return this.appSpringFolderName;
	}

	public void setAppSpringFolderName(String appSpringFolderName) {
		if (appSpringFolderName == null) {
			throw new IllegalArgumentException("appSpringFolderName cannot be null.");
		}

		this.appSpringFolderName = appSpringFolderName;
	}

	public String getTestSpringFolderName() {
		return this.testSpringFolderName;
	}

	public void setTestSpringFolderName(String testSpringFolderName) {
		if (testSpringFolderName == null) {
			throw new IllegalArgumentException("testSpringFolderName cannot be null.");
		}

		this.testSpringFolderName = testSpringFolderName;
	}

	// MARK: Configurations
	@Test
	@Tag(GENERATOR_JUNIT_TEST_FLAG)
	public final void makeConfigurations() throws IOException {
		AppConfiguration configuration = this.makeAppSpringConfiguration();
		this.generateWebInfConfigurations(configuration);
		this.makeTestClentConfiguration(configuration);
	}

	private final void generateWebInfConfigurations(AppConfiguration appConfig) throws IOException {
		GenFilesWriterImpl writer = new GenFilesWriterImpl(this.appWebInfPath);
		this.writeWebInfConfigurations(appConfig, writer);
	}

	protected void writeWebInfConfigurations(AppConfiguration appConfig,
	                                         GenFilesWriter writer)
	        throws IOException {
		this.writeAppSpringFolder(appConfig, writer);
	}

	// MARK: Spring
	public void writeAppSpringFolder(AppConfiguration appConfig,
	                                 GenFilesWriter writer)
	        throws IOException {

		AppConfigurationGenerator generator = new AppConfigurationGenerator(appConfig);
		generator.setAppFolderName(this.appSpringFolderName);

		GenFolder springFolder = generator.generateConfigurations();

		if (this.deleteSpringFolderBeforeGenerating) {
			writer.deleteFolder(springFolder);
		}

		writer.writeFiles(springFolder);
	}

	public abstract AppConfiguration makeAppSpringConfiguration();

	// MARK: Test
	public void makeTestClentConfiguration(AppConfiguration appConfig) throws IOException {

		TestConfigurationGenerator generator = new TestConfigurationGenerator(appConfig);
		GenFolderImpl folder = generator.generateConfigurations();

		folder.setFolderName(this.testSpringFolderName);

		GenFilesWriterImpl writer = new GenFilesWriterImpl(this.testSpringPath);

		if (this.deleteSpringFolderBeforeGenerating) {
			writer.deleteFolder(folder);
		}

		writer.writeFiles(folder);
	}

}
