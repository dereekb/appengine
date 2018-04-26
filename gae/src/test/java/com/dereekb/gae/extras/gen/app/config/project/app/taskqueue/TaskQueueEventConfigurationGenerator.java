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
import com.dereekb.gae.web.taskqueue.server.hook.WebHookTaskQueueTaskControllerEntry;

/**
 * {@link AbstractRemoteModelConfigurationGenerator}
 *
 * @author dereekb
 *
 */
public class TaskQueueEventConfigurationGenerator extends AbstractRemoteModelConfigurationGenerator {

	public static final String EVENT_FOLDER_NAME = "event";
	public static final String EVENT_FILE_NAME = "event";
	public static final String WEBHOOK_FILE_NAME = "webhook";

	public TaskQueueEventConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(EVENT_FOLDER_NAME);
		this.setModelsFolderName(EVENT_FOLDER_NAME);
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
		builder.bean(modelConfig.getModelBeanPrefix() + "EventSerializer").beanClass(ModelEventServiceEntryImpl.class)
		        .c().ref(this.getAppConfig().getAppBeans().getEventServiceId())
		        .ref(modelConfig.getModelKeyListAccessorFactoryId()).ref(modelConfig.getModelDataConverterBeanId());

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
		builder.bean("eventService").beanClass(AppEventServiceImpl.class).c()
		        .ref(this.getAppConfig().getAppBeans().getAppInfoBeanId());
		builder.stringBean("modelEventGroup", "model");

		builder.comment("Multi-event Listener");
		builder.bean("modelKeyEventServiceListener").beanClass(MultiTypeModelKeyEventServiceListener.class).c()
		        .ref("modelKeyEventServiceListenerEntries");

		builder.comment("Entries");
		SpringBeansXMLMapBuilder<SpringBeansXMLBuilder> entriesMap = builder.map("modelKeyEventServiceListenerEntries");

		// TODO: Add each listener to map.

		return this.makeFileWithXML(EVENT_FILE_NAME, builder);
	}

	public GenFile makeModelsWebHookXmlFile() {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		String webHookEventConverterBeanId = "webHookEventConverter";
		String webHookEventSerializerBeanId = "webHookEventSerializer";
		String webHookEventDeserializerBeanId = "webHookEventDeserializer";

		builder.comment("Task Controller");
		builder.bean("webHookTaskQueueTaskControllerEntry").beanClass(WebHookTaskQueueTaskControllerEntry.class).c()
		        .ref(this.getAppConfig().getAppBeans().getEventServiceId()).ref(webHookEventDeserializerBeanId);

		builder.comment("Converter");

		builder.bean(webHookEventConverterBeanId).beanClass(WebHookEventConverterImpl.class).c()
		        .ref(webHookEventSerializerBeanId).ref(webHookEventDeserializerBeanId);

		SpringBeansXMLMapBuilder<?> groupEventSerializer = builder.bean(webHookEventSerializerBeanId).beanClass(GroupWebHookEventSerializerImpl.class).c().map();
		SpringBeansXMLMapBuilder<?> groupEventDeserializer = builder.bean(webHookEventDeserializerBeanId).beanClass(GroupWebHookEventDeserializerImpl.class).c().map();

		// Model
		groupEventSerializer.entry("model").valueRef("modelWebHookEventSerializer");
		groupEventDeserializer.entry("model").valueRef("modelWebHookEventDeserializer");

		builder.comment("Model Converter");
		SpringBeansXMLMapBuilder<?> serializerMapper = builder.bean("modelWebHookEventSerializer")
		        .beanClass(TypedModelWebHookEventSerializerImpl.class).c().map();
		SpringBeansXMLMapBuilder<?> deserializerMapper = builder.bean("modelWebHookEventDeserializer")
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

		return this.makeFileWithXML(WEBHOOK_FILE_NAME, builder);
	}

}
