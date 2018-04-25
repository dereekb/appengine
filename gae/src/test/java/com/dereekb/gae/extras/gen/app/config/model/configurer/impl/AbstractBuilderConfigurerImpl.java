package com.dereekb.gae.extras.gen.app.config.model.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.model.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.model.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link ConfigurerInstance}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractBuilderConfigurerImpl
        implements ConfigurerInstance {

	protected final AppConfiguration appConfig;
	protected final AppModelConfiguration modelConfig;
	protected final SpringBeansXMLBuilder builder;

	public AbstractBuilderConfigurerImpl(AppConfiguration appConfig,
	        AppModelConfiguration modelConfig,
	        SpringBeansXMLBuilder builder) {
		super();

		if (appConfig == null) {
			throw new IllegalArgumentException("appConfig cannot be null.");
		}

		if (modelConfig == null) {
			throw new IllegalArgumentException("modelConfig cannot be null.");
		}

		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null.");
		}

		this.appConfig = appConfig;
		this.modelConfig = modelConfig;
		this.builder = builder;
	}

	public AppConfiguration getAppConfig() {
		return this.appConfig;
	}

	public AppModelConfiguration getModelConfig() {
		return this.modelConfig;
	}

	public SpringBeansXMLBuilder getBuilder() {
		return this.builder;
	}

}
