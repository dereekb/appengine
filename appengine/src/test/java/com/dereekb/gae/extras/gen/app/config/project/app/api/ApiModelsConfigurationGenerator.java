package com.dereekb.gae.extras.gen.app.config.project.app.api;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.filter.NotInternalModelConfigurationFilter;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.extension.inclusion.reader.impl.ModelInclusionReaderImpl;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;
import com.dereekb.gae.web.api.model.crud.controller.impl.EditModelControllerConversionDelegateImpl;
import com.dereekb.gae.web.api.model.crud.controller.impl.EditModelControllerDelegateImpl;
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

		builder.comment("Edit Controller");
		if (modelConfig.hasCreateService() || modelConfig.hasUpdateService() || modelConfig.hasDeleteService()) {

			builder.bean(modelConfig.getModelBeanPrefix() + "EditController")
			        .beanClass(modelConfig.getModelEditControllerClass()).c()
			        .ref(modelConfig.getModelBeanPrefix() + "EditControllerDelegate")
			        .ref(modelConfig.getModelBeanPrefix() + "EditControllerConversionDelegate");

			builder.bean(modelConfig.getModelBeanPrefix() + "EditControllerDelegate")
			        .beanClass(EditModelControllerDelegateImpl.class).c()
			        .ref(modelConfig.getModelBeanPrefix() + "CrudService");

			builder.bean(modelConfig.getModelBeanPrefix() + "EditControllerConversionDelegate")
			        .beanClass(EditModelControllerConversionDelegateImpl.class).c().ref(modelConfig.getModelTypeBeanId())
			        .ref(modelConfig.getStringModelKeyConverter()).ref(modelConfig.getModelDataConverterBeanId());

		} else {
			builder.comment("This type has no edit controllers available.");
		}

		builder.comment("Search/Query Components");
		builder.bean(modelConfig.getModelBeanPrefix() + "SearchDelegateEntry")
		        .beanClass(ApiSearchDelegateEntryImpl.class).c().ref(modelConfig.getModelTypeBeanId())
		        .ref(modelConfig.getModelQueryServiceId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

}
