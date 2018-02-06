package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.model.extension.link.LinkExtensionApiController;
import com.dereekb.gae.web.api.model.extension.search.exception.ApiSearchExceptionHandler;

public class ApiLinkConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String LINK_FILE_NAME = "link";

	public ApiLinkConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(LINK_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Link Exception Handler");
		builder.bean("apiLinkExceptionHandler").beanClass(ApiSearchExceptionHandler.class);

		builder.comment("Link Controller");
		builder.bean("linkExtensionApiController").beanClass(LinkExtensionApiController.class).c()
		        .ref(this.getAppConfig().getAppBeans().getLinkServiceId());

		return builder;
	}

}
