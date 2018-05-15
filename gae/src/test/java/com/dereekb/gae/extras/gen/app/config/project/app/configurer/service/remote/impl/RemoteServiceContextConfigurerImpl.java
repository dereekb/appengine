package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSecurityContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote.RemoteServiceSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link RemoteServiceContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteServiceContextConfigurerImpl
        implements RemoteServiceContextConfigurer {

	private RemoteServiceSecurityContextConfigurer securityContextConfigurer = new RemoteServiceSecurityContextConfigurerImpl();
	private RemoteServiceSpringContextConfigurer springContextConfigurer = new NoOpRemoteServiceSpringContextConfigurerImpl();

	public RemoteServiceSecurityContextConfigurer getSecurityContextConfigurer() {
		return this.securityContextConfigurer;
	}

	public void setSecurityContextConfigurer(RemoteServiceSecurityContextConfigurer securityContextConfigurer) {
		if (securityContextConfigurer == null) {
			throw new IllegalArgumentException("securityContextConfigurer cannot be null.");
		}

		this.securityContextConfigurer = securityContextConfigurer;
	}

	public RemoteServiceSpringContextConfigurer getSpringContextConfigurer() {
		return this.springContextConfigurer;
	}

	public void setSpringContextConfigurer(RemoteServiceSpringContextConfigurer springContextConfigurer) {
		if (springContextConfigurer == null) {
			throw new IllegalArgumentException("springContextConfigurer cannot be null.");
		}

		this.springContextConfigurer = springContextConfigurer;
	}

	// MARK: RemoteServiceContextConfigurer
	@Override
	public void configureRemoteServiceSecurityComponents(AppConfiguration appConfig,
	                                                     RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                     SpringBeansXMLBuilder builder) {
		this.securityContextConfigurer.configureRemoteServiceSecurityComponents(appConfig,
		        appRemoteServiceConfiguration, builder);
	}

	@Override
	public void configureRemoteServiceContextComponents(AppSpringContextType springContext,
	                                                    AppConfiguration appConfig,
	                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                    SpringBeansXMLBuilder builder) {
		this.springContextConfigurer.configureRemoteServiceContextComponents(springContext, appConfig,
		        appRemoteServiceConfiguration, builder);
	}

	// MARK: Internal Classes
	public static class NoOpRemoteServiceSpringContextConfigurerImpl
	        implements RemoteServiceSpringContextConfigurer {

		@Override
		public void configureRemoteServiceContextComponents(AppSpringContextType springContext,
		                                                    AppConfiguration appConfig,
		                                                    RemoteServiceConfiguration appRemoteServiceConfiguration,
		                                                    SpringBeansXMLBuilder builder) {
			// Do nothing.
		}

	}

}
