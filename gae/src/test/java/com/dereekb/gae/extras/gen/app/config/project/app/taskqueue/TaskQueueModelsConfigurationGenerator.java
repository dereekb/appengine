package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.context.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

public class TaskQueueModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";
	public static final String MODELS_FILE_NAME = "model";

	public TaskQueueModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(MODELS_FILE_NAME);
		this.setIgnoreRemote(true);
	}

	public TaskQueueModelsConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		folder.addFile(this.makeModelsXmlFile(folder));

		return folder;
	}

	public GenFile makeModelsXmlFile(GenFolderImpl folder) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Imports");
		builder.importResources(folder.getFiles());

		// NOTE: Deprecated and moved into Iterate. Remove later.
		/*
		 * builder.comment("Edit Controller");
		 * SpringBeansXMLMapBuilder<?> map =
		 * builder.bean("taskQueueEditController")
		 * .beanClass(TaskQueueEditController.class).c()
		 * .ref(this.getAppConfig().getAppBeans().getModelKeyTypeConverterId()).
		 * map();
		 *
		 * for (AppModelConfiguration model :
		 * this.getAllApplicableConfigurations()) {
		 * map.keyValueRefEntry(model.getModelTypeBeanId(),
		 * model.getModelBeanPrefix() + "TaskQueueEditControllerEntry");
		 * }
		 */

		builder.comment("Iterate");
		SpringBeansXMLMapBuilder<?> iterateEntryMap = builder.bean("taskQueueIterateController")
		        .beanClass(TaskQueueIterateController.class).c().ref("taskScheduler").ref("modelKeyTypeConverter")
		        .map();

		for (AppModelConfiguration model : this.getAllApplicableConfigurations()) {
			if (model.getCustomLocalModelContextConfigurer().hasIterateControllerEntry()) {
				iterateEntryMap.keyRefValueRefEntry(model.getModelTypeBeanId(),
				        model.getModelBeanPrefix() + "TaskQueueIterateControllerEntry");
			}
		}

		return this.makeFileWithXML(MODELS_FILE_NAME, builder);
	}

	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		CustomLocalModelContextConfigurer customConfigurer = modelConfig.getCustomLocalModelContextConfigurer();
		customConfigurer.configureIterateControllerTasks(this.getAppConfig(), modelConfig, builder);
		// NOTE: Deprecated and moved into Iterate. Remove later.
		/*
		 * builder.comment("Edit Controller");
		 * builder.bean(modelConfig.getModelBeanPrefix() +
		 * "TaskQueueEditControllerEntry")
		 * .beanClass(TaskQueueEditControllerEntryImpl.class).c().ref(
		 * modelConfig.getModelRegistryId())
		 * .ref(modelConfig.getModelBeanPrefix() + "TaskQueuePostCreateTask")
		 * .ref(modelConfig.getModelBeanPrefix() + "TaskQueuePostUpdateTask")
		 * .ref(modelConfig.getModelBeanPrefix() +
		 * "TaskQueueProcessDeleteTask");
		 *
		 * builder.bean(modelConfig.getModelBeanPrefix() +
		 * "TaskQueuePostCreateTask").beanClass(TaskQueueModelTask.class)
		 * .c().list();
		 *
		 * builder.bean(modelConfig.getModelBeanPrefix() +
		 * "TaskQueuePostUpdateTask").beanClass(TaskQueueModelTask.class)
		 * .c().list();
		 *
		 * builder.bean(modelConfig.getModelBeanPrefix() +
		 * "TaskQueueProcessDeleteTask")
		 * .beanClass(TaskQueueModelTask.class).c().list();
		 */

		return builder;
	}

}
