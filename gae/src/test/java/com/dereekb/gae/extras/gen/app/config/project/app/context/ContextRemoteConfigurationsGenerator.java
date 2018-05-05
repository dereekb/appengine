package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractRemoteServiceConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

/**
 * Used for configuring remote models that are required in the primary context.
 * <p>
 * The configuration produced is usually used for the security context, or model
 * roles checkups.
 *
 * @author dereekb
 *
 */
public class ContextRemoteConfigurationsGenerator extends AbstractRemoteServiceConfigurationGenerator {

	public ContextRemoteConfigurationsGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public ContextRemoteConfigurationsGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setResultsFolderName("remote");
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLServiceConfigurationFile(RemoteServiceConfiguration service)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		service.getRemoteServiceContextConfigurer().configureRemoteServiceContextComponents(AppSpringContextType.SHARED,
		        service, builder);

		return builder;
	}

	@Override
	public String toString() {
		return "ContextRemoteConfigurationsGenerator []";
	}

}
