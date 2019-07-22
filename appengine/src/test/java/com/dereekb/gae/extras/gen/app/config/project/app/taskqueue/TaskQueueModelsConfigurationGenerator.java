package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.filter.NotInternalModelConfigurationFilter;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * Used for generating local model configurations, generally iterate task entries.
 *
 * @author dereekb
 *
 */
public class TaskQueueModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";

	public TaskQueueModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(MODELS_FOLDER_NAME);
		this.setIgnoreRemote(true);
		this.setIgnoreInternalOnly(true);
	}

	public TaskQueueModelsConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		return folder;
	}

	// MARK: Primary File
	@Override
	public SpringBeansXMLBuilder makePrimaryFolderImportFileBuilder(GenFolder primary) {
		SpringBeansXMLBuilder builder = super.makePrimaryFolderImportFileBuilder(primary);

		builder.comment("Iterate");
		SpringBeansXMLMapBuilder<?> iterateEntryMap = builder.bean("taskQueueIterateController")
		        .beanClass(TaskQueueIterateController.class).c().ref("taskScheduler").ref("modelKeyTypeConverter")
		        .map();

		for (LocalModelConfiguration model : this.getAllLocalConfigurations(NotInternalModelConfigurationFilter.make())) {
			if (model.getCustomModelContextConfigurer().hasIterateControllerEntry()) {
				iterateEntryMap.keyRefValueRefEntry(model.getModelTypeBeanId(),
				        model.getModelBeanPrefix() + "TaskQueueIterateControllerEntry");
			}
		}

		return builder;
	}


	// MARK: Models
	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		LocalModelContextConfigurer customConfigurer = modelConfig.getCustomModelContextConfigurer();
		customConfigurer.configureIterateControllerTasks(this.getAppConfig(), modelConfig, builder);

		return builder;
	}

}
