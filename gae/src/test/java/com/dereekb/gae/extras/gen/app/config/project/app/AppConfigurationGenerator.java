package com.dereekb.gae.extras.gen.app.config.project.app;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.api.ApiConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.context.ContextConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.taskqueue.TaskQueueConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.utilities.misc.path.PathUtility;

/**
 * Used for generating Spring configuration for an app.
 *
 * @author dereekb
 *
 */
public class AppConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public static final String APP_FILE_NAME = "app";
	public static final String APP_FOLDER_NAME = "app";

	private String appFileName = APP_FILE_NAME;
	private String appFolderName = APP_FOLDER_NAME;

	public AppConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public AppConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	public String getAppFileName() {
		return this.appFileName;
	}

	public void setAppFileName(String appFileName) {
		if (appFileName == null) {
			throw new IllegalArgumentException("appFileName cannot be null.");
		}

		this.appFileName = appFileName;
	}

	public String getAppFolderName() {
		return this.appFolderName;
	}

	public void setAppFolderName(String appFolderName) {
		if (appFolderName == null) {
			throw new IllegalArgumentException("appFolderName cannot be null.");
		}

		this.appFolderName = appFolderName;
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl(this.appFolderName);

		// Api
		folder.addFolder(new ApiConfigurationGenerator(this).generateConfigurations());

		// Context
		folder.addFolder(new ContextConfigurationGenerator(this).generateConfigurations());

		// Taskqueue
		folder.addFolder(new TaskQueueConfigurationGenerator(this).generateConfigurations());

		folder.addFile(this.makeAppFile(folder));

		return folder;
	}

	public GenFile makeAppFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		for (GenFolder subFolder : folder.getFolders()) {
			builder.importResource(
			        PathUtility.buildPath(subFolder.getFolderName(), subFolder.getFolderName() + ".xml"));
		}

		return this.makeFileWithXML(this.appFileName, builder);
	}

}
