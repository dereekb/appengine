package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl;

import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * {@link RemoteModelSpringContextConfigurer} implementation that wraps several
 * implementations.
 *
 * @author dereekb
 *
 */
public class RemoteModelSpringContextConfigurerImpl
        implements RemoteModelSpringContextConfigurer {

	private HashMapWithList<AppSpringContextType, RemoteModelSpringContextConfigurer> configurers;

	public RemoteModelSpringContextConfigurerImpl(
	        HashMapWithList<AppSpringContextType, RemoteModelSpringContextConfigurer> configurers) {
		this.setConfigurers(configurers);
	}

	public HashMapWithList<AppSpringContextType, RemoteModelSpringContextConfigurer> getConfigurers() {
		return this.configurers;
	}

	public void setConfigurers(HashMapWithList<AppSpringContextType, RemoteModelSpringContextConfigurer> configurers) {
		if (configurers == null) {
			throw new IllegalArgumentException("configurers cannot be null.");
		}

		this.configurers = configurers;
	}

	// MARK: RemoteModelSpringContextConfigurer
	@Override
	public void configureRemoteModelContextComponents(AppSpringContextType springContext,
	                                                  AppConfiguration appConfig,
	                                                  RemoteServiceConfiguration remoteServiceConfig,
	                                                  RemoteModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		List<RemoteModelSpringContextConfigurer> configurersList = this.configurers.get(springContext);

		if (configurersList == null) {
			return;
		}

		for (RemoteModelSpringContextConfigurer configurer : configurersList) {
			configurer.configureRemoteModelContextComponents(springContext, appConfig, remoteServiceConfig, modelConfig,
			        builder);
		}
	}

	@Override
	public String toString() {
		return "RemoteModelSpringContextConfigurerImpl [configurers=" + this.configurers + "]";
	}

}
