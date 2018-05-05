package com.dereekb.gae.extras.gen.app.gae.remote;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteServiceConfigurationImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl.RemoteServiceContextConfigurerImpl;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * {@link Factory} for {@link RemoteServiceConfiguration} values.
 *
 * @author dereekb
 *
 */
public class AbstractRemoteServiceConfigurationGen
        implements Factory<RemoteServiceConfiguration> {

	private AppServiceConfigurationInfo serviceConfigurationInfo;

	public AbstractRemoteServiceConfigurationGen(AppServiceConfigurationInfo serviceConfigurationInfo) {
		this.setServiceConfigurationInfo(serviceConfigurationInfo);
	}

	public AppServiceConfigurationInfo getServiceConfigurationInfo() {
		return this.serviceConfigurationInfo;
	}

	public void setServiceConfigurationInfo(AppServiceConfigurationInfo serviceConfigurationInfo) {
		if (serviceConfigurationInfo == null) {
			throw new IllegalArgumentException("serviceConfigurationInfo cannot be null.");
		}

		this.serviceConfigurationInfo = serviceConfigurationInfo;
	}

	// MARK: Factory
	@Override
	public RemoteServiceConfigurationImpl make() throws FactoryMakeFailureException {

		RemoteServiceContextConfigurer configurer = this.makeRemoteServiceContextConfigurer();
		List<RemoteModelConfigurationGroup> models = this.makeModelsConfiguration();

		RemoteServiceConfigurationImpl config = new RemoteServiceConfigurationImpl(this.serviceConfigurationInfo,
		        configurer, models);

		return config;
	}

	protected RemoteServiceContextConfigurer makeRemoteServiceContextConfigurer() {
		RemoteServiceContextConfigurerImpl configurer = new RemoteServiceContextConfigurerImpl();

		return configurer;
	}

	protected List<RemoteModelConfigurationGroup> makeModelsConfiguration() {
		return Collections.emptyList();
	}

}
