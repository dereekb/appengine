package com.dereekb.gae.extras.gen.app.config.app.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelCrudsConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl.ClientRemoteModelSpringContextConfigurerBuilder;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl.RemoteModelContextConfigurerImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link RemoteModelConfiguration} implementation.
 */
public class RemoteModelConfigurationImpl extends AppModelConfigurationImpl<RemoteModelCrudsConfiguration, RemoteModelBeansConfiguration, RemoteModelContextConfigurer>
        implements RemoteModelConfiguration, RemoteModelCrudsConfiguration, RemoteModelBeansConfiguration {

	public RemoteModelConfigurationImpl(Class<?> modelClass) {
		super(modelClass, false);
	}

	public RemoteModelConfigurationImpl(Class<?> modelClass, ModelKeyType modelKeyType) {
		super(modelClass, modelKeyType, false);
	}

	public RemoteModelConfigurationImpl(Class<?> modelClass,
	        ModelKeyType modelKeyType,
	        String modelType) {
		super(modelClass, modelKeyType, modelType, false);
	}

	@Override
	public boolean isLocalModel() {
		return false;
	}

	@Override
	public void setLocalModel(boolean localModel) {
		super.setLocalModel(false);
	}

	// MARK: AppModelConfigurationImpl
	@Override
	protected RemoteModelCrudsConfiguration inferCrudsConfiguration() {
		return new RemoteModelCrudsConfigurationImpl();
	}

	@Override
	protected RemoteModelBeansConfiguration makeModelBeansConfiguration() {
		return new RemoteModelBeansConfigurationImpl(this.getModelType(), this.getModelKeyType());
	}

	@Override
	protected RemoteModelContextConfigurer makeCustomModelContextConfigurer() {
		RemoteModelContextConfigurerImpl configurer = new RemoteModelContextConfigurerImpl();

		// Set the default CRUDS configurer.
		configurer.setSpringContextConfigurer(ClientRemoteModelSpringContextConfigurerBuilder.makeDefault());

		return configurer;
	}

	// MARK: RemoteModelBeansConfiguration
	@Override
	public String getModelClientCrudServiceBeanId() {
		return this.getBeansConfiguration().getModelClientCrudServiceBeanId();
	}

	@Override
	public String getModelClientCreateServiceBeanId() {
		return this.getBeansConfiguration().getModelClientCreateServiceBeanId();
	}

	@Override
	public String getModelClientReadServiceBeanId() {
		return this.getBeansConfiguration().getModelClientReadServiceBeanId();
	}

	@Override
	public String getModelClientUpdateServiceBeanId() {
		return this.getBeansConfiguration().getModelClientUpdateServiceBeanId();
	}

	@Override
	public String getModelClientDeleteServiceBeanId() {
		return this.getBeansConfiguration().getModelClientDeleteServiceBeanId();
	}

	@Override
	public String getModelClientQueryServiceBeanId() {
		return this.getBeansConfiguration().getModelClientQueryServiceBeanId();
	}


}
