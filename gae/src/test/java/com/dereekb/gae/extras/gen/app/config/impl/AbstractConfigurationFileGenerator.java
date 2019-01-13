package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.ConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.impl.XMLGenFileImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.XMLBuilderObject;
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
		SpringBeansXMLBuilder builder = this.makeImportFileBuilder(folder, importResources, importSubFoldersMainFile);
		return this.makeFileWithXML(filename, builder);
	}

	public SpringBeansXMLBuilder makeImportFileBuilder(GenFolder folder,
	                                                   boolean importResources,
	                                                   boolean importSubFoldersMainFile) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Import");
		this.importFilesWithBuilder(builder, folder, importResources, importSubFoldersMainFile);

		return builder;
	}

	public List<GenFolder> generateImportFilesForFolders(List<GenFolder> folder) {
		return this.generateImportFilesForFolders(folder, false);
	}

	public List<GenFolder> generateImportFilesForFolders(List<GenFolder> folders,
	                                                     boolean recursive) {
		List<GenFolder> newFolders = new ArrayList<GenFolder>();

		for (GenFolder folder : folders) {
			GenFolderImpl newFolder = new GenFolderImpl(folder);
			GenFile importFile = this.makeImportFile(folder);

			if (!newFolder.hasFileWithName(importFile.getFileName())) {
				newFolder.addFile(importFile);
			}

			if (recursive) {
				// TODO: Add recursion.
				throw new UnsupportedOperationException("Recursion not yet implemented.");
			}

			newFolders.add(newFolder);
		}

		return newFolders;
	}

	public void importFilesWithBuilder(SpringBeansXMLBuilder builder,
	                                   GenFolder folder,
	                                   boolean importFolderResources,
	                                   boolean importSubFoldersMainFile) {

		if (importFolderResources) {
			builder.importResources(folder.getFiles());
		}

		if (importSubFoldersMainFile) {
			List<GenFolder> subFolders = folder.getFolders();

			for (GenFolder subFolder : subFolders) {
				GenFile file = subFolder.getFileWithName(subFolder.getFolderName());

				if (file != null) {
					builder.importResource(subFolder.getFolderName() + "/" + file.getOutputFileName());
				}
			}
		}
	}

	public GenFile makeFileWithXML(String fileName,
	                               XMLBuilderObject builder) {
		return this.makeFileWithXML(fileName, builder.getRawXMLBuilder());
	}

	public GenFile makeFileWithXML(String fileName,
	                               XMLBuilder2 result) {
		return new XMLGenFileImpl(fileName, result, this.getOutputProperties());
	}

}
