package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.ConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
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

	public AbstractConfigurationFileGenerator(AbstractConfigurationFileGenerator generator) {
		this(generator.getAppConfig(), generator.getOutputProperties());
	}

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
		return this.makeImportFile(folder, false);
	}

	public GenFile makeImportFile(GenFolder folder,
	                              boolean importSubFoldersMainFile) {
		return this.makeImportFile(folder.getFolderName(), folder, importSubFoldersMainFile);
	}

	public GenFile makeImportFile(String filename,
	                              GenFolder folder,
	                              boolean importSubFoldersMainFile) {
		return this.makeImportFile(filename, folder, true, importSubFoldersMainFile);
	}

	public GenFile makeImportFile(GenFolder folder,
	                              boolean importResources,
	                              boolean importSubFoldersMainFile) {
		return this.makeImportFile(folder.getFolderName(), folder, true, importSubFoldersMainFile);
	}

	public GenFile makeImportFile(String filename,
	                              GenFolder folder,
	                              boolean importResources,
	                              boolean importSubFoldersMainFile) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Import");
		builder.importResources(folder.getFiles());

		if (importSubFoldersMainFile) {
			List<GenFolder> subFolders = folder.getFolders();

			for (GenFolder subFolder : subFolders) {
				GenFile file = subFolder.getFileWithName(subFolder.getFolderName());

				if (file != null) {
					builder.importResource(subFolder.getFolderName() + "/" + file.getOutputFileName());
				}
			}
		}

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
