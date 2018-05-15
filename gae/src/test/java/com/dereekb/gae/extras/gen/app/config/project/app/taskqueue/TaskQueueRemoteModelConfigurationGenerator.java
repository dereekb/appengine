package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelWebHookEventDeserializerImpl;

/**
 * {@link AbstractRemoteModelConfigurationGenerator}
 *
 * @author dereekb
 *
 */
public class TaskQueueRemoteModelConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String REMOTE_MODEL_FOLDER_NAME = "remote";

	public TaskQueueRemoteModelConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setSplitByRemote(false);
		this.setIgnoreLocal(true);
		this.setSplitByModel(true);
		this.setResultsFolderName(REMOTE_MODEL_FOLDER_NAME);
	}

	public TaskQueueRemoteModelConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makePrimaryFolderImportFileBuilder(GenFolder primary) {
		SpringBeansXMLBuilder builder = super.makePrimaryFolderImportFileBuilder(primary);

		return builder;
	}

	@Override
	public SpringBeansXMLBuilder makeXMLRemoteModelClientConfigurationFile(RemoteModelConfiguration modelConfig) {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Remote Model");

		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventDeserializer")
		        .beanClass(ModelWebHookEventDeserializerImpl.class).c()
		        .ref(modelConfig.getModelKeyListAccessorFactoryId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

}
