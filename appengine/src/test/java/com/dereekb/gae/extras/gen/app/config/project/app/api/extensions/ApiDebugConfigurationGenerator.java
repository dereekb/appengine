package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppDebugApiConfigurer;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.utilities.misc.env.EnvStringUtility;

/**
 * Generates debug API configuration for the initialization of an app.
 *
 * @author dereekb
 *
 */
public class ApiDebugConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String DEBUG_FILE_NAME = "debug";

	public ApiDebugConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(DEBUG_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		AppDebugApiConfigurer appDebugApiConfigurer = this.getAppConfig().getAppServicesConfigurer()
		        .getAppDebugApiConfigurer();

		// Do not generate the debug controller in a production environment.
		if (appDebugApiConfigurer != null && !EnvStringUtility.isProduction()) {
			appDebugApiConfigurer.configureDebugApiController(this.getAppConfig(), builder);
		}

		return builder;
	}

}
