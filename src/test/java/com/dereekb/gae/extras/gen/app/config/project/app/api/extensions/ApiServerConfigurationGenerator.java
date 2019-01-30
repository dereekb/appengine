package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

/**
 * Generates server configuration for the initialization of an app.
 *
 * @author dereekb
 *
 */
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

		AppServerInitializationConfigurer initializationConfigurer = this.getAppConfig().getAppServicesConfigurer().getAppServerInitializationConfigurer();

		if (initializationConfigurer != null) {
			initializationConfigurer.configureServerInitializationComponents(this.getAppConfig(), builder);
		}

		return builder;
	}

}
