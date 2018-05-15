package com.dereekb.gae.extras.gen.app.config.project.app.taskqueue;

import java.util.Map;
import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomLocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.server.event.event.service.impl.AppEventServiceImpl;
import com.dereekb.gae.server.event.model.shared.event.service.impl.ModelEventServiceEntryImpl;
import com.dereekb.gae.server.event.model.shared.event.service.listener.impl.MultiTypeModelKeyEventServiceListener;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelWebHookEventDeserializerImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.ModelWebHookEventSerializerImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.TypedModelWebHookEventDeserializerImpl;
import com.dereekb.gae.server.event.model.shared.webhook.impl.TypedModelWebHookEventSerializerImpl;
import com.dereekb.gae.server.event.webhook.service.impl.GroupWebHookEventDeserializerImpl;
import com.dereekb.gae.server.event.webhook.service.impl.GroupWebHookEventSerializerImpl;
import com.dereekb.gae.server.event.webhook.service.impl.WebHookEventConverterImpl;
import com.dereekb.gae.web.taskqueue.server.webhook.TaskQueueWebHookController;
import com.dereekb.gae.web.taskqueue.server.webhook.impl.TaskQueueWebHookControllerDelegateImpl;

/**
 * {@link AbstractRemoteModelConfigurationGenerator}
 *
 * @author dereekb
 *
 */
public class TaskQueueEventConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String EVENT_FOLDER_NAME = "event";
	public static final String EVENT_FILE_NAME = "event";
	public static final String WEBHOOK_FILE_NAME = "webhook";

	public TaskQueueEventConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setSplitByModel(false);
		this.setSplitByGroup(false);
		this.setSplitByRemote(true);
		this.setIgnoreRemote(false);
		this.setMakeImportFiles(true);
		this.setResultsFolderName(EVENT_FOLDER_NAME);
	}

	public TaskQueueEventConfigurationGenerator(AppConfiguration appConfig) {
		super(appConfig);
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		folder.addFile(this.makeModelsWebHookXmlFile());

		return folder;
	}

	// MARK: Models
	/**
	 * Local models get an event service, and model
	 * serialization/deserialization.
	 */
	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Model Event Service");
		builder.bean(modelConfig.getModelEventServiceEntryBeanId()).beanClass(ModelEventServiceEntryImpl.class).c()
		        .ref(this.getAppConfig().getAppBeans().getEventServiceId())
		        .ref(modelConfig.getModelKeyListAccessorFactoryId());

		builder.comment("Web Hook Serializers");
		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventSerializer")
		        .beanClass(ModelWebHookEventSerializerImpl.class).c().ref(modelConfig.getModelDataConverterBeanId());

		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventDeserializer")
		        .beanClass(ModelWebHookEventDeserializerImpl.class).c()
		        .ref(modelConfig.getModelKeyListAccessorFactoryId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

	/**
	 * Remote models only get deserializers.
	 */
	@Override
	public SpringBeansXMLBuilder makeXMLRemoteModelClientConfigurationFile(RemoteModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Remote Model");
		builder.bean(modelConfig.getModelBeanPrefix() + "WebHookEventDeserializer")
		        .beanClass(ModelWebHookEventDeserializerImpl.class).c()
		        .ref(modelConfig.getModelKeyListAccessorFactoryId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

	// MARK: XML Files
	@Override
	public SpringBeansXMLBuilder makePrimaryFolderImportFileBuilder(GenFolder primary) {
		SpringBeansXMLBuilder builder = super.makePrimaryFolderImportFileBuilder(primary);

		builder.imp("/webhook.xml");

		builder.comment("Shared");
		String eventServiceListenersBeanId = "eventServiceListeners";

		builder.bean("eventService").beanClass(AppEventServiceImpl.class).c()
		        .ref(this.getAppConfig().getAppBeans().getAppInfoBeanId()).ref(eventServiceListenersBeanId);
		builder.stringBean("modelEventGroup", "model");

		builder.comment("Event Listener");

		String webHookEventSubmitterBeanId = this.getAppConfig().getAppBeans().getWebHookEventSubmitterBeanId();
		String modelKeyEventServiceListenerBeanId = "modelKeyEventServiceListener";

		builder.list(eventServiceListenersBeanId).ref(modelKeyEventServiceListenerBeanId)
		        .ref(webHookEventSubmitterBeanId);

		builder.comment("Multi-event Listener");
		builder.bean("modelKeyEventServiceListener").beanClass(MultiTypeModelKeyEventServiceListener.class).c()
		        .ref("modelKeyEventServiceListenerEntries");

		builder.comment("Entries");
		SpringBeansXMLMapBuilder<SpringBeansXMLBuilder> entriesMap = builder.map("modelKeyEventServiceListenerEntries");

		Map<String, String> appListenerEntries = this.getAppConfig().getAppServicesConfigurer().getAppEventListenerConfigurer().configureEventListenerEntries(this.getAppConfig(), builder);
		entriesMap.getRawXMLBuilder().c("App Entries");

		entriesMap.keyValueRefEntries(appListenerEntries);

		entriesMap.getRawXMLBuilder().c("Model Entries");
		for (LocalModelConfiguration model : this.getAllLocalConfigurations()) {
			CustomLocalModelContextConfigurer modelConfigurer = model.getCustomModelContextConfigurer();

			Map<String, String> entries = modelConfigurer.configureEventListenerEntries(this.getAppConfig(), model, builder);
			entriesMap.keyValueRefEntries(entries);
		}

		return builder;
	}

	public GenFile makeModelsWebHookXmlFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		String webHookEventSubmitterBeanId = this.getAppConfig().getAppBeans().getWebHookEventSubmitterBeanId();
		String webHookEventConverterBeanId = this.getAppConfig().getAppBeans().getWebHookEventConverterBeanId();

		String webHookEventSerializerBeanId = "webHookEventSerializer";
		String webHookEventDeserializerBeanId = "webHookEventDeserializer";

		// Controller
		builder.comment("Task Controller");

		String taskQueueWebHookControllerDelegateBeanId = "taskQueueWebHookControllerDelegate";

		builder.bean("taskQueueWebHookController").beanClass(TaskQueueWebHookController.class).c()
		        .ref(taskQueueWebHookControllerDelegateBeanId);

		builder.bean(taskQueueWebHookControllerDelegateBeanId).beanClass(TaskQueueWebHookControllerDelegateImpl.class)
		        .c().ref(this.getAppConfig().getAppBeans().getEventServiceId()).ref(webHookEventDeserializerBeanId)
		        .ref(webHookEventSubmitterBeanId);

		builder.comment("Event Submitter / Listener");
		this.getAppConfig().getAppServicesConfigurer().getAppWebHookEventServiceConfigurer()
		        .configureWebHookEventSubmitter(this.getAppConfig(), builder);

		// Converter
		builder.comment("Converter");
		builder.bean(webHookEventConverterBeanId).beanClass(WebHookEventConverterImpl.class).c()
		        .ref(webHookEventSerializerBeanId).ref(webHookEventDeserializerBeanId);

		SpringBeansXMLMapBuilder<?> groupEventSerializer = builder.bean(webHookEventSerializerBeanId)
		        .beanClass(GroupWebHookEventSerializerImpl.class).c().map();
		SpringBeansXMLMapBuilder<?> groupEventDeserializer = builder.bean(webHookEventDeserializerBeanId)
		        .beanClass(GroupWebHookEventDeserializerImpl.class).c().map();

		// Model
		groupEventSerializer.entry("model").valueRef("modelWebHookEventSerializer");
		groupEventDeserializer.entry("model").valueRef("modelWebHookEventDeserializer");

		builder.comment("Model Converter");
		SpringBeansXMLMapBuilder<?> serializerMapper = builder.bean("modelWebHookEventSerializer")
		        .beanClass(TypedModelWebHookEventSerializerImpl.class).c().map();
		SpringBeansXMLMapBuilder<?> deserializerMapper = builder.bean("modelWebHookEventDeserializer")
		        .beanClass(TypedModelWebHookEventDeserializerImpl.class).c().map();

		// Make Typed Serializers
		for (AppModelConfiguration model : this.getAllApplicableModelConfigurations()) {

			// Only Add Serializers for local models.
			if (model.isLocalModel()) {
				String serializerId = model.getModelBeanPrefix() + "WebHookEventSerializer";
				serializerMapper.keyRefValueRefEntry(model.getModelTypeBeanId(), serializerId);
			}

			String deserializerId = model.getModelBeanPrefix() + "WebHookEventDeserializer";
			deserializerMapper.keyRefValueRefEntry(model.getModelTypeBeanId(), deserializerId);
		}

		return this.makeFileWithXML(WEBHOOK_FILE_NAME, builder);
	}

}
