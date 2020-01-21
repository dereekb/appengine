package com.dereekb.gae.extras.gen.app.gae.remote;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.impl.AppServiceConfigurationInfoImpl;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfigurationGroup;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl.ClientRemoteServiceSpringContextConfigurerBuilder;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl.RemoteServiceContextConfigurerImpl;
import com.dereekb.gae.extras.gen.app.gae.local.LoginGroupConfigurationGen;

/**
 * {@link RemoteServiceConfigurationGen} for the login service.
 *
 * @author dereekb
 *
 */
public class RemoteLoginServiceConfigurationGen extends AbstractRemoteServiceConfigurationGen {

	public static final String DEFAULT_SERVICE_NAME = "login";

	private boolean makeLoginGroupConfiguration = true;

	public RemoteLoginServiceConfigurationGen(String projectAppId, String appVersion, String apiVersion) {
		super(new AppServiceConfigurationInfoImpl(projectAppId, DEFAULT_SERVICE_NAME, appVersion, apiVersion));
	}

	public boolean isMakeLoginGroupConfiguration() {
		return this.makeLoginGroupConfiguration;
	}

	public void setMakeLoginGroupConfiguration(boolean makeLoginGroupConfiguration) {
		this.makeLoginGroupConfiguration = makeLoginGroupConfiguration;
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

	@Override
	protected List<RemoteModelConfigurationGroup> makeModelsConfiguration() {
		List<RemoteModelConfigurationGroup> groups = new ArrayList<RemoteModelConfigurationGroup>();

		if (this.makeLoginGroupConfiguration) {
			groups.add(LoginGroupConfigurationGen.makeRemoteLoginGroupConfig());
		}

		return groups;
	}

}
