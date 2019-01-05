package com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link ConfigurerInstance} that takes in a {@link AppModelConfiguration}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppModelBuilderConfigurerImpl extends AbstractAppBuilderConfigurerImpl {

	protected final AppModelConfiguration modelConfig;

	public AbstractAppModelBuilderConfigurerImpl(AppConfiguration appConfig,
	        AppModelConfiguration modelConfig,
	        SpringBeansXMLBuilder builder) {
		super(appConfig, builder);

		if (modelConfig == null) {
			throw new IllegalArgumentException("modelConfig cannot be null.");
		}

		this.modelConfig = modelConfig;
	}

	public AppModelConfiguration getModelConfig() {
		return this.modelConfig;
	}

}
