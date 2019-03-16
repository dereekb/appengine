package com.dereekb.gae.extras.gen.app.gae.remote;

import com.dereekb.gae.extras.gen.app.config.app.impl.AppServiceConfigurationInfoImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl.ClientRemoteServiceSpringContextConfigurerBuilder;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl.RemoteServiceContextConfigurerImpl;

/**
 * {@link RemoteServiceConfigurationGen} for the file service.
 *
 * @author dereekb
 *
 */
public class RemoteFileServiceConfigurationGen extends AbstractRemoteServiceConfigurationGen {

	public static final String DEFAULT_SERVICE_NAME = "file";

	public RemoteFileServiceConfigurationGen(String projectAppId) {
		super(new AppServiceConfigurationInfoImpl(projectAppId, DEFAULT_SERVICE_NAME));
	}

	public RemoteFileServiceConfigurationGen(String projectAppId, String appVersion) {
		super(new AppServiceConfigurationInfoImpl(projectAppId, DEFAULT_SERVICE_NAME, appVersion));
	}

	// MARK: Override
	@Override
	protected RemoteServiceContextConfigurer makeRemoteServiceContextConfigurer() {
		RemoteServiceContextConfigurerImpl configurer = new RemoteServiceContextConfigurerImpl();

		// Spring Config
		ClientRemoteServiceSpringContextConfigurerBuilder springConfigurerBuilder = new ClientRemoteServiceSpringContextConfigurerBuilder();

		// TODO: Configure?

		configurer.setSpringContextConfigurer(springConfigurerBuilder.make());

		return configurer;
	}

	/*
	 * @Override
	 * protected List<RemoteModelConfigurationGroup> makeModelsConfiguration() {
	 * return ListUtility.toList();
	 * }
	 */
}
