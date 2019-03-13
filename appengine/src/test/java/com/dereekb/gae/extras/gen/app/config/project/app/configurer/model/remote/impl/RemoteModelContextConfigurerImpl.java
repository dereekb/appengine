package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelSpringContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link RemoteModelContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteModelContextConfigurerImpl
        implements RemoteModelContextConfigurer {

	private RemoteModelSpringContextConfigurer springContextConfigurer = new NoOpRemoteModelSpringContextConfigurerImpl();

	public RemoteModelSpringContextConfigurer getSpringContextConfigurer() {
		return this.springContextConfigurer;
	}

	public void setSpringContextConfigurer(RemoteModelSpringContextConfigurer springContextConfigurer) {
		if (springContextConfigurer == null) {
			throw new IllegalArgumentException("springContextConfigurer cannot be null.");
		}

		this.springContextConfigurer = springContextConfigurer;
	}

	// MARK: RemoteModelContextConfigurer
	@Override
	public void configureRemoteModelContextComponents(AppSpringContextType springContext,
	                                                  AppConfiguration appConfig,
	                                                  RemoteServiceConfiguration remoteServiceConfig,
	                                                  RemoteModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		this.springContextConfigurer.configureRemoteModelContextComponents(springContext, appConfig,
		        remoteServiceConfig, modelConfig, builder);
	}

	// MARK: Internal Classes
	public static class NoOpRemoteModelSpringContextConfigurerImpl
	        implements RemoteModelSpringContextConfigurer {

		@Override
		public void configureRemoteModelContextComponents(AppSpringContextType springContext,
		                                                  AppConfiguration appConfig,
		                                                  RemoteServiceConfiguration remoteServiceConfig,
		                                                  RemoteModelConfiguration modelConfig,
		                                                  SpringBeansXMLBuilder builder) {
			// Do nothing.
		}

	}

}
