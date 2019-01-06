package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractRemoteServiceConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.extras.gen.utility.GenFolder;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

/**
 * Used for configuring remote models that are required in the primary/shared
 * context.
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
	public void makeServiceConfiguration(GenFolderImpl serviceResultsFolder,
	                                     RemoteServiceConfiguration service) {
		// Services
		GenFile serviceConfigFile = this.makeServiceConfigurationFile(service);
		serviceResultsFolder.addFile(serviceConfigFile);

		// Models
		GenFolder serviceModelsFolder = this.makeModelConfigurationsForService(service);
		serviceResultsFolder.addFolder(serviceModelsFolder);
	}

	// MARK: Services
	@Override
	public SpringBeansXMLBuilder makeXMLServiceConfigurationFile(RemoteServiceConfiguration service)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Remote Service Security");
		service.getRemoteServiceContextConfigurer().configureRemoteServiceSecurityComponents(this.getAppConfig(),
		        service, builder);

		builder.comment("Remote Service Shared Components");
		service.getRemoteServiceContextConfigurer().configureRemoteServiceContextComponents(AppSpringContextType.SHARED,
		        this.getAppConfig(), service, builder);

		return builder;
	}

	// MARK: Models
	protected GenFolder makeModelConfigurationsForService(RemoteServiceConfiguration service) {
		return new GenFolderImpl("models", service.getRemoteServiceContextConfigurer()
		        .configureRemoteServiceModelComponents(AppSpringContextType.SHARED, this.getAppConfig(), service));
	}

	@Override
	public String toString() {
		return "ContextRemoteConfigurationsGenerator []";
	}

}
