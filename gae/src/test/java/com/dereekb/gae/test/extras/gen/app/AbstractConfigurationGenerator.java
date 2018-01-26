package com.dereekb.gae.test.extras.gen.app;

import java.util.Properties;

import com.dereekb.gae.test.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.test.extras.gen.utility.GenFile;
import com.dereekb.gae.test.extras.gen.utility.GenFolder;
import com.dereekb.gae.test.extras.gen.utility.impl.XMLGenFileImpl;
import com.dereekb.gae.test.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.jamesmurty.utils.XMLBuilder2;

public abstract class AbstractConfigurationGenerator {

	private AppConfiguration appConfig;
	private Properties outputProperties;

	public AbstractConfigurationGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public AbstractConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		this.setAppConfig(appConfig);
		this.setOutputProperties(outputProperties);
	}

	public AppConfiguration getAppConfig() {
		return this.appConfig;
	}

	public void setAppConfig(AppConfiguration appConfig) {
		if (appConfig == null) {
			throw new IllegalArgumentException("appConfig cannot be null.");
		}

		this.appConfig = appConfig;
	}

	public Properties getOutputProperties() {
		return this.outputProperties;
	}

	public void setOutputProperties(Properties outputProperties) {
		this.outputProperties = outputProperties;
	}

	// MARK: Generation
	public abstract GenFolder generateConfigurations();

	// MARK: Utility
	public GenFile makeFileWithXML(String fileName, SpringBeansXMLBuilder builder) {
		return this.makeFileWithXML(fileName, builder.getRawXMLBuilder());
	}

	public GenFile makeFileWithXML(String fileName, XMLBuilder2 result) {
		return new XMLGenFileImpl(fileName, result, this.getOutputProperties());
	}

}
