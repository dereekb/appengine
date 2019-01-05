package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring the CRUD services for the local model.
 *
 * @author dereekb
 *
 */
public interface RemoteModelCrudConfigurer {

	public void configureRemoteCrudServiceComponents(AppConfiguration appConfig,
	                                                 RemoteModelConfiguration remoteModelConfig,
	                                                 SpringBeansXMLBuilder builder);

}
