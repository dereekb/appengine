package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelSpringContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.shared.remote.AbstractAppSpringContextTypeFilteredConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link RemoteModelSpringContextConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractRemoteModelSpringContextConfigurer extends AbstractAppSpringContextTypeFilteredConfigurer
        implements RemoteModelSpringContextConfigurer {

	public AbstractRemoteModelSpringContextConfigurer() {
		super();
	}

	public AbstractRemoteModelSpringContextConfigurer(AppSpringContextType springContext) {
		super(springContext);
	}

	// MAARK: RemoteServiceSpringContextConfigurer
	@Override
	public void configureRemoteModelContextComponents(AppSpringContextType springContext,
	                                                  AppConfiguration appConfig,
	                                                  RemoteServiceConfiguration remoteServiceConfig,
	                                                  RemoteModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder) {
		if (this.matchesSpringContext(springContext)) {
			this.configureRemoteModelContextComponents(appConfig, remoteServiceConfig, modelConfig, builder);
		}
	}

	public abstract void configureRemoteModelContextComponents(AppConfiguration appConfig,
	                                                           RemoteServiceConfiguration remoteServiceConfig,
	                                                           RemoteModelConfiguration modelConfig,
	                                                           SpringBeansXMLBuilder builder);

}
