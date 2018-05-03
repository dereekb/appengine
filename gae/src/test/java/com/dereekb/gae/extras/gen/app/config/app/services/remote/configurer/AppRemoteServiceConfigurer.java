package com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * Used for configuring a remote service's beans.
 *
 * @author dereekb
 *
 */
public interface AppRemoteServiceConfigurer {

	/**
	 * Configures components required in every context in the app.
	 *
	 * @param appConfig
	 *            {@link AppConfiguration} implementation. Never {@code null}.
	 * @param builder
	 *            {@link SpringBeansXMLBuilder}. Never {@code null}.
	 */
	public void configureSharedContextComponents(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder);

	/**
	 * Configures components required in only the taskqueue context.
	 *
	 * @param appConfig
	 *            {@link AppConfiguration} implementation. Never {@code null}.
	 * @param builder
	 *            {@link SpringBeansXMLBuilder}. Never {@code null}.
	 */
	public void configureTaskQueueContextComponents(AppConfiguration appConfig,
	                                                SpringBeansXMLBuilder builder);

}
