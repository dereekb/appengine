package com.dereekb.gae.extras.gen.app.config.project.app.configurer.service.remote;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Interface used for configuring security components for a
 * {@link RemoteModelConfiguration}.
 *
 * @author dereekb
 *
 */
public interface RemoteServiceSecurityContextConfigurer {

	public void configureRemoteServiceSecurityComponents(AppConfiguration appConfig,
	                                                     RemoteServiceConfiguration appRemoteServiceConfiguration,
	                                                     SpringBeansXMLBuilder builder);

}
