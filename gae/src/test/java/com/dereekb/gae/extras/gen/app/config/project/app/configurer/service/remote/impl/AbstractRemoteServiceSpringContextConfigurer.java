package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link RemoteServiceSpringContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractRemoteServiceSpringContextConfigurer
        implements RemoteServiceSpringContextConfigurer {

	private AppSpringContextType springContext;

	public AbstractRemoteServiceSpringContextConfigurer() {
		this(AppSpringContextType.API);
	}

	public AbstractRemoteServiceSpringContextConfigurer(AppSpringContextType springContext) {
		super();
		this.springContext = springContext;
	}

	public AppSpringContextType getSpringContext() {
		return this.springContext;
	}

	public void setSpringContext(AppSpringContextType springContext) {
		if (springContext == null) {
			throw new IllegalArgumentException("springContext cannot be null.");
		}

		this.springContext = springContext;
	}

	// MAARK: RemoteServiceSpringContextConfigurer
	@Override
	public void configureRemoteServiceContextComponents(AppSpringContextType springContext,
	                                                    AppConfiguration appConfig,
	                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                    SpringBeansXMLBuilder builder) {
		if (this.springContext == springContext) {
			this.configureRemoteServiceContextComponents(appConfig, appRemoteServiceConfiguration, builder);
		}
	}

	public abstract void configureRemoteServiceContextComponents(AppConfiguration appConfiguration,
	                                                             RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                             SpringBeansXMLBuilder builder);

}
