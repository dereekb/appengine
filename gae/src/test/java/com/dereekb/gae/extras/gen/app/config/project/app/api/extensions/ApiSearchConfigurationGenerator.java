package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.model.extension.search.SearchExtensionApiController;
import com.dereekb.gae.web.api.model.extension.search.exception.ApiSearchExceptionHandler;
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

		builder.comment("Search Exception Handler");
		builder.bean("apiSearchExceptionHandler").beanClass(ApiSearchExceptionHandler.class);

		builder.comment("Search Controller");
		builder.bean("searchExtensionApiController").beanClass(SearchExtensionApiController.class).c()
		        .ref("searchExtensionApiControllerDelegate");

		SpringBeansXMLMapBuilder<?> searchMap = builder.bean("searchExtensionApiControllerDelegate")
		        .beanClass(ApiSearchDelegateImpl.class).c().map();

		for (AppModelConfigurationGroup group : this.getAppConfig().getModelConfigurations()) {
			for (AppModelConfiguration model : group.getModelConfigurations()) {
				if (model.isLocalModel()) {
					searchMap.keyRefValueRefEntry(model.getModelTypeBeanId(),
					        model.getModelBeanPrefix() + "SearchDelegateEntry");
				}
			}
		}

		return builder;
	}

}
