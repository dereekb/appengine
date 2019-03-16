package com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link ConfigurerInstance}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractLocalModelBuilderConfigurerImpl extends AbstractAppModelBuilderConfigurerImpl {

	public AbstractLocalModelBuilderConfigurerImpl(AppConfiguration appConfig,
	        LocalModelConfiguration modelConfig,
	        SpringBeansXMLBuilder builder) {
		super(appConfig, modelConfig, builder);
	}

	@Override
	public LocalModelConfiguration getModelConfig() {
		return (LocalModelConfiguration) this.modelConfig;
	}

}
