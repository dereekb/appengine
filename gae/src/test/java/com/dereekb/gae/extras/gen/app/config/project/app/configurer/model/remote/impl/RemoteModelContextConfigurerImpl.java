package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

// MARK: RemoteModelContextConfigurer
public class RemoteModelContextConfigurerImpl
        implements RemoteModelContextConfigurer {

	// MARK: RemoteModelContextConfigurer
	@Override
	public void configureRemoteModelContextComponents(AppSpringContextType springContext,
	                                                  RemoteServiceConfiguration remoteServiceConfig,
	                                                  RemoteModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {



	}

}
