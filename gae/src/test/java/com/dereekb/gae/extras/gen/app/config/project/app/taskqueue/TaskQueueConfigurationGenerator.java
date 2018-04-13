package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.utilities.misc.path.PathUtility;
import com.dereekb.gae.web.taskqueue.server.task.TaskQueueTaskController;

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

		folder.addFile(this.makeTaskQueueFile());

		// Models
		TaskQueueModelsConfigurationGenerator modelsGen = new TaskQueueModelsConfigurationGenerator(this.getAppConfig(),
		        this.getOutputProperties());
		folder.addFolder(modelsGen.generateConfigurations());

		// Extensions
		folder.addFolder(this.generateExtensions());

		return folder;
	}

	public GenFile makeTaskQueueFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Models");
		builder.imp("/model/model.xml");

		builder.comment("Extensions");
		builder.imp("/extension/extension.xml");

		builder.comment("Task");
		builder.bean("taskQueueTaskController").beanClass(TaskQueueTaskController.class).c()
		        .ref("taskQueueTaskControllerEntries");

		builder.map("taskQueueTaskControllerEntries").keyType(String.class).entry("webhooks")
		        .valueRef("scheduleWebHookEvent");

		return this.makeFileWithXML("taskqueue", builder);
	}

	public GenFolderImpl generateExtensions() {
		GenFolderImpl extensions = new GenFolderImpl("extension");

		SpringBeansXMLBuilder extensionsFile = SpringBeansXMLBuilderImpl.make();

		// Remote Models
		TaskQueueRemoteModelConfigurationGenerator remoteGen = new TaskQueueRemoteModelConfigurationGenerator(
		        this.getAppConfig(), this.getOutputProperties());
		GenFolderImpl remoteFolder = remoteGen.generateConfigurations();
		extensions.addFolder(remoteFolder);
		extensionsFile.importResource(PathUtility.buildPath(TaskQueueRemoteModelConfigurationGenerator.REMOTE_MODEL_FOLDER_NAME,
				TaskQueueRemoteModelConfigurationGenerator.REMOTE_MODEL_FILE_NAME + ".xml"));

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
