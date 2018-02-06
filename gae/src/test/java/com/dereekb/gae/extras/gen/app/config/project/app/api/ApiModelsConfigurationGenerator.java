package com.dereekb.gae.extras.gen.app.config.project.app.api;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.model.extension.inclusion.reader.impl.ModelInclusionReaderImpl;
import com.dereekb.gae.web.api.model.crud.controller.ReadController;
import com.dereekb.gae.web.api.model.crud.impl.EditModelControllerConversionDelegateImpl;
import com.dereekb.gae.web.api.model.crud.impl.EditModelControllerDelegateImpl;
import com.dereekb.gae.web.api.model.crud.impl.ReadControllerEntryImpl;
import com.dereekb.gae.web.api.model.extension.search.impl.ApiSearchDelegateEntryImpl;

public class ApiModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

	public static final String MODELS_FOLDER_NAME = "model";
	public static final String MODELS_FILE_NAME = "model";

	public ApiModelsConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName(MODELS_FILE_NAME);
		this.setIgnoreRemote(true);
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

		builder.comment("Read Controller");
		SpringBeansXMLMapBuilder<?> readControllerMap = builder.bean("readController").beanClass(ReadController.class)
		        .lazy(false).c().ref(this.getAppConfig().getAppBeans().getModelKeyTypeConverterId()).map();

		for (AppModelConfiguration model : this.getAllApplicableConfigurations()) {
			readControllerMap.keyValueRefEntry(model.getModelTypeBeanId(),
			        model.getModelBeanPrefix() + "ReadControllerEntry");
		}

		return this.makeFileWithXML(MODELS_FILE_NAME, builder);
	}

	@Override
	public SpringBeansXMLBuilder makeXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Read Controller Entry");
		builder.bean(modelConfig.getModelBeanPrefix() + "ReadControllerEntry").beanClass(ReadControllerEntryImpl.class)
		        .lazy(false).c().ref(modelConfig.getModelReadServiceId()).ref(modelConfig.getModelDataConverterBeanId())
		        .ref(modelConfig.getModelInclusionReaderId());

		builder.bean(modelConfig.getModelInclusionReaderId()).beanClass(ModelInclusionReaderImpl.class).c()
		        .ref(modelConfig.getModelLinkModelAccessorId());

		builder.comment("Edit Controller");
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

		builder.comment("Search/Query Components");
		builder.bean(modelConfig.getModelBeanPrefix() + "SearchDelegateEntry")
		        .beanClass(ApiSearchDelegateEntryImpl.class).c().ref(modelConfig.getModelTypeBeanId())
		        .ref(modelConfig.getModelQueryServiceId()).ref(modelConfig.getModelDataConverterBeanId());

		return builder;
	}

}
