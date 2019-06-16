package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.model.extension.search.SearchExtensionApiController;
import com.dereekb.gae.web.api.model.extension.search.impl.ApiSearchDelegateImpl;

public class ApiSearchConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String SEARCH_FILE_NAME = "search";

	public ApiSearchConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(SEARCH_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Search Controller");
		builder.bean("searchExtensionApiController").beanClass(SearchExtensionApiController.class).c()
		        .ref("searchExtensionApiControllerDelegate");

		SpringBeansXMLMapBuilder<?> searchMap = builder.bean("searchExtensionApiControllerDelegate")
		        .beanClass(ApiSearchDelegateImpl.class).c().map();

		for (LocalModelConfigurationGroup group : this.getAppConfig().getLocalModelConfigurations()) {
			for (LocalModelConfiguration model : group.getModelConfigurations()) {
				searchMap.keyRefValueRefEntry(model.getModelTypeBeanId(),
				        model.getModelBeanPrefix() + "SearchDelegateEntry");
			}
		}

		return builder;
	}

}
