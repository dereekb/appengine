package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Interface used for configuring components for a
 * {@link RemoteModelConfiguration} within a particular
 * {@link AppSpringContextType}.
 *
 * @author dereekb
 *
 */
public interface RemoteModelSpringContextConfigurer {

	public void configureRemoteModelContextComponents(AppSpringContextType springContext,
	                                                  AppConfiguration appConfig,
	                                                  RemoteServiceConfiguration remoteServiceConfig,
	                                                  RemoteModelConfiguration modelConfig,
	                                                  SpringBeansXMLBuilder builder);

}
