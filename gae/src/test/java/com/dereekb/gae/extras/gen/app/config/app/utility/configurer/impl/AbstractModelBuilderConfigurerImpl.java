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
public abstract class AbstractModelBuilderConfigurerImpl extends AbstractAppBuilderConfigurerImpl {

	protected final LocalModelConfiguration modelConfig;

	public AbstractModelBuilderConfigurerImpl(AppConfiguration appConfig,
	        LocalModelConfiguration modelConfig,
	        SpringBeansXMLBuilder builder) {
		super(appConfig, builder);

		if (modelConfig == null) {
			throw new IllegalArgumentException("modelConfig cannot be null.");
		}

		this.modelConfig = modelConfig;
	}

	public LocalModelConfiguration getModelConfig() {
		return this.modelConfig;
	}

}
