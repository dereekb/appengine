package com.dereekb.gae.extras.gen.app.config.app.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelCrudsConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.impl.AppModelConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl.CustomRemoteModelContextConfigurerImpl;
import com.dereekb.gae.server.datastore.models.keys.ModelKeyType;

/**
 * {@link RemoteModelConfiguration} implementation.
 */
public class RemoteModelConfigurationImpl extends AppModelConfigurationImpl<RemoteModelCrudsConfiguration, RemoteModelBeansConfiguration>
        implements RemoteModelConfiguration, RemoteModelCrudsConfiguration, RemoteModelBeansConfiguration {

	public RemoteModelConfigurationImpl(Class<?> modelClass) {
		super(modelClass);
	}

	public RemoteModelConfigurationImpl(Class<?> modelClass, ModelKeyType modelKeyType) {
		super(modelClass, modelKeyType);
	}

	public RemoteModelConfigurationImpl(Class<?> modelClass, ModelKeyType modelKeyType, boolean localModel) {
		super(modelClass, modelKeyType, localModel);
	}

	public RemoteModelConfigurationImpl(Class<?> modelClass,
	        ModelKeyType modelKeyType,
	        String modelType,
	        boolean localModel) {
		super(modelClass, modelKeyType, modelType, localModel);
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
	protected CustomModelContextConfigurer makeCustomModelContextConfigurer() {
		return new CustomRemoteModelContextConfigurerImpl();
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
