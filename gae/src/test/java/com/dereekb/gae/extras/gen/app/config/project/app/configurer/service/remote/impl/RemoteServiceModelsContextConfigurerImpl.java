package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractModelConfigurationGenerator;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceModelsContextConfigurer;
import com.dereekb.gae.extras.gen.utility.impl.GenFolderImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;

/**
 * {@link RemoteServiceModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteServiceModelsContextConfigurerImpl
        implements RemoteServiceModelsContextConfigurer {

	private boolean splitModelConfigs = false;
	private Properties outputProperties = null;

	public boolean isSplitModelConfigs() {
		return this.splitModelConfigs;
	}

	public void setSplitModelConfigs(boolean splitModelConfigs) {
		this.splitModelConfigs = splitModelConfigs;
	}

	public Properties getOutputProperties() {
		return this.outputProperties;
	}

	public void setOutputProperties(Properties outputProperties) {
		this.outputProperties = outputProperties;
	}

	// MARK: RemoteServiceModelsContextConfigurer
	@Override
	public GenFolderImpl configureRemoteServiceModelComponents(AppSpringContextType springContext,
	                                                           AppConfiguration appConfig,
	                                                           RemoteServiceConfiguration appRemoteServiceConfiguration) {
		return new ContextRemoteModelsConfigurationGenerator(springContext, appConfig, appRemoteServiceConfiguration)
		        .makeModelConfigurationsForService();
	}

	protected class ContextRemoteModelsConfigurationGenerator extends AbstractModelConfigurationGenerator {

		private final AppSpringContextType springContext;
		private final RemoteServiceConfiguration remoteServiceConfig;

		public ContextRemoteModelsConfigurationGenerator(AppSpringContextType springContext,
		        AppConfiguration appConfig,
		        RemoteServiceConfiguration appRemoteServiceConfiguration) {
			super(appConfig, RemoteServiceModelsContextConfigurerImpl.this.outputProperties);
			this.setIgnoreLocal(true);
			this.setIgnoreRemote(false);
			this.setSplitByGroup(false);
			this.setSplitByModel(RemoteServiceModelsContextConfigurerImpl.this.splitModelConfigs);

			if (springContext == null) {
				throw new IllegalArgumentException("springContext cannot be null.");
			}

			this.springContext = springContext;

			if (appRemoteServiceConfiguration == null) {
				throw new IllegalArgumentException("appRemoteServiceConfiguration cannot be null.");
			}

			this.remoteServiceConfig = appRemoteServiceConfiguration;
		}

		public AppSpringContextType getSpringContext() {
			return this.springContext;
		}

		public RemoteServiceConfiguration getRemoteServiceConfig() {
			return this.remoteServiceConfig;
		}

		// MARK: Model Configurations
		public GenFolderImpl makeModelConfigurationsForService() {
			return this.makeGeneratorForConfiguration()
			        .makeRemoteModelConfigurations(this.remoteServiceConfig.getServiceModelConfigurations());
		}

		// MARK: Remote Model Generation
		@Override
		public SpringBeansXMLBuilder makeXMLRemoteModelClientConfigurationFile(RemoteModelConfiguration modelConfig)
		        throws UnsupportedOperationException {
			SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();
			modelConfig.getCustomModelContextConfigurer().configureRemoteModelContextComponents(this.springContext,
			        this.getAppConfig(), this.remoteServiceConfig, modelConfig, builder);
			return builder;
		}

	}

}
