package com.dereekb.gae.extras.gen.app.config.impl;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AbstractConfigurationFileGenerator} extension targeted for a single file.
 *
 * @author dereekb
 *
 */
public class AbstractSingleConfigurationFileGenerator extends AbstractConfigurationFileGenerator {

	private String folderName = "result";
	private String fileName = "result";

	public AbstractSingleConfigurationFileGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
	}

	public AbstractSingleConfigurationFileGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	public AbstractSingleConfigurationFileGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public String getFolderName() {
		return this.folderName;
	}

	public void setFolderName(String folderName) {
		if (folderName == null) {
			throw new IllegalArgumentException("folderName cannot be null.");
		}

		this.folderName = folderName;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		if (fileName == null) {
			throw new IllegalArgumentException("fileName cannot be null.");
		}

		this.fileName = fileName;
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {

		GenFolderImpl folder = new GenFolderImpl(this.folderName);

		folder.addFile(this.generateConfigurationFile());

		return folder;
	}

	public GenFile generateConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = this.makeXMLConfigurationFile();
		return this.makeFileWithXML(this.fileName, builder);
	}

	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		throw new UnsupportedOperationException("Override in subclass to use.");
	}

}
