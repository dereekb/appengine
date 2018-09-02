package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

public class ApiServerConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String SERVER_FILE_NAME = "server";

	public ApiServerConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(SERVER_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		// The Root Service has no initialization...
		// TODO: Interface with the login server to set the app as initialized or whatnot.
		/*
		if (this.getAppConfig().isRootServer() == false) {
			builder.comment("Initialize Server Controller");
			builder.bean("apiInitializeServerController").beanClass(ApiInitializeServerController.class).c()
			        .ref("apiInitializeServerControllerDelegate");
			builder.bean("apiInitializeServerControllerDelegate")
			        .beanClass(ApiInitializeServerControllerDelegateImpl.class).c()
			        .ref(this.getAppConfig().getAppBeans().getAppInfoBeanId()).ref("appRegistry");
		}
		*/

		return builder;
	}

}
