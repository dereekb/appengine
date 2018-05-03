package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractRemoteModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
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
public class TaskQueueRemoteModelConfigurationGenerator extends AbstractRemoteModelConfigurationGenerator {

	public static final String REMOTE_MODEL_FOLDER_NAME = "remote";
	public static final String REMOTE_MODEL_FILE_NAME = "remote";

	public TaskQueueRemoteModelConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setSplitByRemote(false);
		this.setIgnoreLocal(true);
		this.setSplitByModel(true);
		this.setResultsFolderName(REMOTE_MODEL_FOLDER_NAME);
		this.setModelsFolderName(REMOTE_MODEL_FOLDER_NAME);
	}

	public TaskQueueRemoteModelConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		GenFile remote = makeImportFile(folder);
		folder.addFile(remote);

		// folder.addFile(this.makeModelsXmlFile());

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makeRemoteXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Remote Model");

		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventDeserializer")
		        .beanClass(ModelWebHookEventDeserializerImpl.class).c()
		        .ref(modelConfig.getModelKeyListAccessorFactoryId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

	// MARK: XML Files
	public GenFile makeModelsXmlFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Imports");
		builder.imp("/local/local.xml");
		builder.imp("/remote/remote.xml");
		builder.imp("/webhook.xml");

		return this.makeFileWithXML(REMOTE_MODEL_FILE_NAME, builder);
	}

}
