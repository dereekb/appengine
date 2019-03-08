package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.utilities.misc.path.PathUtility;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskController;

/**
 * The primary TaskQueue configuration generator for an app.
 *
 * @author dereekb
 *
 */
public class TaskQueueConfigurationGenerator extends AbstractConfigurationFileGenerator {

	public TaskQueueConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
	}

	public TaskQueueConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	public TaskQueueConfigurationGenerator(AbstractConfigurationFileGenerator generator) {
		super(generator);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public GenFolder generateConfigurations() {
		GenFolderImpl folder = new GenFolderImpl("taskqueue");

		// Models
		TaskQueueModelsConfigurationGenerator modelsGen = new TaskQueueModelsConfigurationGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		folder.addFolder(modelsGen.generateConfigurations());

		// Remote
		TaskQueueRemoteConfigurationsGenerator remoteGen = new TaskQueueRemoteConfigurationsGenerator(
		        this.getAppConfig(), this.getOutputProperties());
		folder.addFolder(remoteGen.generateConfigurations());

		// Extensions
		folder.addFolder(this.generateExtensions());

		// Task Queue File
		folder.addFile(this.makeTaskQueueFile(folder));

		return folder;
	}

	public GenFile makeTaskQueueFile(GenFolder folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Imports");
		this.importFilesWithBuilder(builder, folder, true, true);

		builder.comment("Task");
		builder.bean("taskQueueTaskController").beanClass(TaskQueueTaskController.class).c()
		        .ref("taskQueueTaskControllerEntries");

		SpringBeansXMLMapBuilder<?> map = builder.map("taskQueueTaskControllerEntries").keyType(String.class);

		// TODO: Add custom taskqueue controller entries!
		map.up();

		return this.makeFileWithXML("taskqueue", builder);
	}

	public GenFolderImpl generateExtensions() {
		GenFolderImpl extensions = new GenFolderImpl("extension");

		SpringBeansXMLBuilder extensionsFile = SpringBeansXMLBuilderImpl.make();

		// Events
		TaskQueueEventConfigurationGenerator eventGen = new TaskQueueEventConfigurationGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		GenFolderImpl eventFolder = eventGen.generateConfigurations();
		extensions.addFolder(eventFolder);
		extensionsFile.importResource(PathUtility.buildPath(TaskQueueEventConfigurationGenerator.EVENT_FOLDER_NAME,
		        TaskQueueEventConfigurationGenerator.EVENT_FILE_NAME + ".xml"));

		// Other Extensions

		// Extension File
		GenFile extensionFile = this.makeFileWithXML("extension", extensionsFile);
		extensions.addFile(extensionFile);

		return extensions;
	}

}
