package com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link ConfigurerInstance}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractRemoteModelBuilderConfigurerImpl extends AbstractAppModelBuilderConfigurerImpl {

	public AbstractRemoteModelBuilderConfigurerImpl(AppConfiguration appConfig,
	        RemoteModelConfiguration modelConfig,
	        SpringBeansXMLBuilder builder) {
		super(appConfig, modelConfig, builder);
	}

	@Override
	public RemoteModelConfiguration getModelConfig() {
		return (RemoteModelConfiguration) this.modelConfig;
	}

}
