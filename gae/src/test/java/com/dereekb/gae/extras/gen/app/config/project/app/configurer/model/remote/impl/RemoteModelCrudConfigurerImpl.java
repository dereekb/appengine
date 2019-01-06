package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl.AbstractRemoteModelBuilderConfigurerImpl;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.remote.RemoteModelCrudConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link RemoteModelCrudConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteModelCrudConfigurerImpl
        implements RemoteModelCrudConfigurer {

	@Override
	public void configureRemoteCrudServiceComponents(AppConfiguration appConfig,
	                                                 RemoteModelConfiguration remoteModelConfig,
	                                                 SpringBeansXMLBuilder builder) {
		new RemoteModelCrudConfigurerInstance(appConfig, remoteModelConfig, builder).configure();
	}

	public static class RemoteModelCrudConfigurerInstance extends AbstractRemoteModelBuilderConfigurerImpl {

		public RemoteModelCrudConfigurerInstance(AppConfiguration appConfig,
		        RemoteModelConfiguration modelConfig,
		        SpringBeansXMLBuilder builder) {
			super(appConfig, modelConfig, builder);
		}

		// MARK: AbstractBuilderConfigurer
		@Override
		public void configure() {
			// TODO: Configure!
		}

	}

}
