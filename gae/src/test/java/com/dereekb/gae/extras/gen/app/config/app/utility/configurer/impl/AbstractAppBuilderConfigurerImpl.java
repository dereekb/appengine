package com.dereekb.gae.extras.gen.app.config.app.utility.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.configurer.ConfigurerInstance;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Abstract {@link ConfigurerInstance}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppBuilderConfigurerImpl
        implements ConfigurerInstance {

	protected final AppConfiguration appConfig;
	protected final SpringBeansXMLBuilder builder;

	public AbstractAppBuilderConfigurerImpl(AppConfiguration appConfig,
	        SpringBeansXMLBuilder builder) {
		super();

		if (appConfig == null) {
			throw new IllegalArgumentException("appConfig cannot be null.");
		}

		if (builder == null) {
			throw new IllegalArgumentException("builder cannot be null.");
		}

		this.appConfig = appConfig;
		this.builder = builder;
	}

	public AppConfiguration getAppConfig() {
		return this.appConfig;
	}

	public SpringBeansXMLBuilder getBuilder() {
		return this.builder;
	}

}
