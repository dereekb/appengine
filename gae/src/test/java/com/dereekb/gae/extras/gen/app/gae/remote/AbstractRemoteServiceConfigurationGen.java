package com.dereekb.gae.extras.gen.app.gae.remote;

import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.AppRemoteServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.impl.AppRemoteServiceConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.AppRemoteServiceConfigurationImpl;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * {@link Factory} for {@link AppRemoteServiceConfiguration} values.
 *
 * @author dereekb
 *
 */
public class AbstractRemoteServiceConfigurationGen
        implements Factory<AppRemoteServiceConfiguration> {

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
	public AppRemoteServiceConfigurationImpl make() throws FactoryMakeFailureException {

		AppRemoteServiceConfigurer configurer = this.makeRemoteServiceConfigurer();
		List<AppModelConfigurationGroup> models = this.makeModelsConfiguration();

		AppRemoteServiceConfigurationImpl config = new AppRemoteServiceConfigurationImpl(this.serviceConfigurationInfo,
		        configurer, models);

		return config;
	}

	protected AppRemoteServiceConfigurer makeRemoteServiceConfigurer() {
		AppRemoteServiceConfigurerImpl configurer = new AppRemoteServiceConfigurerImpl();

		return configurer;
	}

	protected List<AppModelConfigurationGroup> makeModelsConfiguration() {
		return Collections.emptyList();
	}

}
