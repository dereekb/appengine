package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.ConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.XMLGenFileImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * Abstract {@link ConfigurationFileGenerator} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractConfigurationFileGenerator
        implements ConfigurationFileGenerator {

	private AppConfiguration appConfig;
	private Properties outputProperties;

	public AbstractConfigurationFileGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public AbstractConfigurationFileGenerator(AppConfiguration appConfig, Properties outputProperties) {
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
	@Override
	public abstract GenFolder generateConfigurations();

	// MARK: Utility
	public GenFile makeImportFile(GenFolder folder) {
		return this.makeImportFile(folder.getFolderName(), folder);
	}

	public GenFile makeImportFile(String filename,
	                              GenFolder folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.importResources(folder.getFiles());

		return this.makeFileWithXML(filename, builder);
	}

	public GenFile makeFileWithXML(String fileName,
	                               SpringBeansXMLBuilder builder) {
		return this.makeFileWithXML(fileName, builder.getRawXMLBuilder());
	}

	public GenFile makeFileWithXML(String fileName,
	                               XMLBuilder2 result) {
		return new XMLGenFileImpl(fileName, result, this.getOutputProperties());
	}

}
