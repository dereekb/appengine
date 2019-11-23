package com.dereekb.gae.extras.gen.app.config.project.app.api;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.filter.NotInternalModelConfigurationFilter;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.local.LocalModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanConstructorBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.extension.inclusion.reader.impl.ModelInclusionReaderImpl;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;
import com.dereekb.gae.web.api.model.crud.controller.impl.ReadControllerEntryImpl;
import com.dereekb.gae.web.api.model.extension.search.impl.ApiSearchDelegateEntryImpl;

/**
 * Used for generating configuration for all local model's CRUD services.
 *
 * @author dereekb
 *
 */
public class ApiModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";

	public ApiModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(MODELS_FOLDER_NAME);
		this.setIgnoreRemote(true);
		this.setIgnoreInternalOnly(true);
	}

	// MARK: AbstractModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makePrimaryFolderImportFileBuilder(GenFolder primary) {
		SpringBeansXMLBuilder builder = super.makePrimaryFolderImportFileBuilder(primary);

		builder.comment("Read Controller");
		SpringBeansXMLMapBuilder<?> readControllerMap = builder.bean("readController").beanClass(ReadController.class)
		        .lazy(false).c().ref(this.getAppConfig().getAppBeans().getModelKeyTypeConverterId()).map();

		for (LocalModelConfiguration model : this
		        .getAllLocalConfigurations(NotInternalModelConfigurationFilter.make())) {
			readControllerMap.keyRefValueRefEntry(model.getModelTypeBeanId(),
			        model.getModelBeanPrefix() + "ReadControllerEntry");
		}

		return builder;
	}

	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(LocalModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Read Controller Entry");
		builder.bean(modelConfig.getModelBeanPrefix() + "ReadControllerEntry").beanClass(ReadControllerEntryImpl.class)
		        .lazy(false).c().ref(modelConfig.getModelReadServiceId()).ref(modelConfig.getModelDataConverterBeanId())
		        .ref(modelConfig.getModelInclusionReaderId());

		builder.bean(modelConfig.getModelInclusionReaderId()).beanClass(ModelInclusionReaderImpl.class).c()
		        .ref(modelConfig.getModelLinkModelAccessorId());

		LocalModelContextConfigurer contextConfigurer = modelConfig.getCustomModelContextConfigurer();

		builder.comment("Edit Controller");
		contextConfigurer.configureApiEditController(this.getAppConfig(), modelConfig, builder);

		builder.comment("Search/Query Components");
		SpringBeansXMLBeanConstructorBuilder<SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder>> searchDelegateConstructor = builder
		        .bean(modelConfig.getModelBeanPrefix() + "SearchDelegateEntry")
		        .beanClass(ApiSearchDelegateEntryImpl.class).c().ref(modelConfig.getModelTypeBeanId())
		        .ref(modelConfig.getModelQueryServiceId());

		// Add the TypedModelSearchService if available
		if (contextConfigurer.hasSearchComponents() == true) {
			searchDelegateConstructor.ref(modelConfig.getUtilityBeans().getTypedModelSearchServiceBeanId());
		}

		searchDelegateConstructor.ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

}
