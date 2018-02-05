package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractRemoteModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.event.model.shared.event.service.impl.ModelEventServiceEntryImpl;
import com.dereekb.gae.server.event.model.shared.event.service.listener.impl.MultiTypeModelKeyEventServiceListener;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelWebHookEventDeserializerImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelWebHookEventSerializerImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.TypedModelWebHookEventDeserializerImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.TypedModelWebHookEventSerializerImpl;

/**
 * {@link AbstractRemoteModelConfigurationGenerator}
 *
 * @author dereekb
 *
 */
public class TaskQueueEventConfigurationGenerator extends AbstractRemoteModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";
	public static final String MODELS_FILE_NAME = "model";
	public static final String MODELS_WEBHOOK_FILE_NAME = "webhook";

	public TaskQueueEventConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(MODELS_FOLDER_NAME);
	}

	public TaskQueueEventConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		folder.addFile(this.makeModelsXmlFile());
		folder.addFile(this.makeModelsWebHookXmlFile());

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makeLocalXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Model Event Service");
		builder.bean(modelConfig.getModelBeanPrefix() + "EventSerializer")
		        .beanClass(ModelEventServiceEntryImpl.class).c().ref(this.getAppConfig().getAppBeans().getEventServiceId());

		builder.comment("Web Hook Serializers");
		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventSerializer")
		        .beanClass(ModelWebHookEventSerializerImpl.class).c().ref(modelConfig.getModelDataConverterBeanId());

		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventDeserializer")
		        .beanClass(ModelWebHookEventDeserializerImpl.class).c()
		        .ref(modelConfig.getModelKeyListAccessorFactoryId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

	@Override
	public SpringBeansXMLBuilder makeRemoteXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
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

		builder.comment("Shared");
		builder.stringBean("modelEventGroup", "model");

		builder.comment("Multi-event Listener");
		builder.bean("modelKeyEventServiceListener").beanClass(MultiTypeModelKeyEventServiceListener.class).c()
		        .ref("modelKeyEventServiceListenerEntries");

		builder.comment("Entries");
		builder.list("modelKeyEventServiceListenerEntries");

		// TODO: Add Listeners

		return this.makeFileWithXML(MODELS_FILE_NAME, builder);
	}

	public GenFile makeModelsWebHookXmlFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Converter");

		SpringBeansXMLMapBuilder<?> serializerMapper = builder.bean("typedModelWebHookEventSerializer")
		        .beanClass(TypedModelWebHookEventSerializerImpl.class).c().map();
		SpringBeansXMLMapBuilder<?> deserializerMapper = builder.bean("typedModelWebHookEventDeserializer")
		        .beanClass(TypedModelWebHookEventDeserializerImpl.class).c().map();

		// Make Typed Serializers
		for (AppModelConfiguration model : this.getAllApplicableConfigurations()) {

			// Only Add Serializers for local models.
			if (model.isLocalModel()) {
				String serializerId = model.getModelBeanPrefix() + "WebHookEventSerializer";
				serializerMapper.keyRefValueRefEntry(model.getModelTypeBeanId(), serializerId);
			}

			String deserializerId = model.getModelBeanPrefix() + "WebHookEventDeserializer";
			deserializerMapper.keyRefValueRefEntry(model.getModelTypeBeanId(), deserializerId);
		}

		return this.makeFileWithXML(MODELS_WEBHOOK_FILE_NAME, builder);
	}

}
