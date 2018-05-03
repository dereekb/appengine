package com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

public interface AppRemoteServiceContextConfigurer {

	public void configureContextComponents(AppConfiguration appConfig,
	                                       SpringBeansXMLBuilder builder);

}
