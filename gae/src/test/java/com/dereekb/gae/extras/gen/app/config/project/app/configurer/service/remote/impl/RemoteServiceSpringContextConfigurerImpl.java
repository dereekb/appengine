package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * {@link RemoteServiceSpringContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteServiceSpringContextConfigurerImpl
        implements RemoteServiceSpringContextConfigurer {

	private HashMapWithList<AppSpringContextType, RemoteServiceSpringContextConfigurer> configurers;

	public RemoteServiceSpringContextConfigurerImpl(
	        HashMapWithList<AppSpringContextType, RemoteServiceSpringContextConfigurer> configurers) {
		this.setConfigurers(configurers);
	}

	public HashMapWithList<AppSpringContextType, RemoteServiceSpringContextConfigurer> getConfigurers() {
		return this.configurers;
	}

	public void setConfigurers(HashMapWithList<AppSpringContextType, RemoteServiceSpringContextConfigurer> configurers) {
		if (configurers == null) {
			throw new IllegalArgumentException("configurers cannot be null.");
		}

		this.configurers = configurers;
	}

	// MARK: RemoteServiceSpringContextConfigurer
	@Override
	public void configureRemoteServiceContextComponents(AppSpringContextType springContext,
	                                                    AppConfiguration appConfig,
	                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                    SpringBeansXMLBuilder builder) {
		List<RemoteServiceSpringContextConfigurer> configurersList = this.configurers.get(springContext);

		if (configurersList == null) {
			return;
		}

		for (RemoteServiceSpringContextConfigurer configurer : configurersList) {
			configurer.configureRemoteServiceContextComponents(springContext, appConfig, appRemoteServiceConfiguration,
			        builder);
		}
	}

	@Override
	public String toString() {
		return "RemoteServiceSpringContextConfigurerImpl [configurers=" + this.configurers + "]";
	}

}
