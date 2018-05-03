package com.dereekb.gae.extras.gen.app.config.project.app.context;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractRemoteModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
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
public class ContextRemoteConfigurationsGenerator extends AbstractRemoteModelConfigurationGenerator {

	public ContextRemoteConfigurationsGenerator(AppConfiguration appConfig) {
		this(appConfig, null);
	}

	public ContextRemoteConfigurationsGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setIgnoreLocal(true);	// No Local Models
		this.setRemoteFolderName(this.getModelsFolderName());
		this.setModelsFolderName("remote");
	}

	// MARK: AbstractRemoteModelConfigurationGenerator
	@Override
	public GenFolderImpl generateConfigurations() {
		GenFolderImpl folder = super.generateConfigurations();

		return folder;
	}

	@Override
	public SpringBeansXMLBuilder makeRemoteXMLModelClientConfigurationFile(AppModelConfiguration modelConfig)
	        throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Shared Remote Model Components");
		modelConfig.getCustomModelContextConfigurer().configureRemoteModelSharedContextComponents(this.getAppConfig(),
		        modelConfig, builder);

		return builder;
	}

	@Override
	public String toString() {
		return "ContextRemoteConfigurationsGenerator []";
	}

}
