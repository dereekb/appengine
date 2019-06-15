package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;
import com.dereekb.gae.utilities.model.source.impl.SourceImpl;
import com.dereekb.gae.utilities.spring.initializer.impl.SpringInitializerImpl;
import com.dereekb.gae.web.api.server.initialize.ApiInitializeServerController;

/**
 * Abstract {@link AppServerInitializationConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppServerInitializationConfigurerImpl
        implements AppServerInitializationConfigurer {

	public static final String DEFAULT_SERVER_INITILIZER_BEAN_ID = "serverInitializer";
	public static final String DEFAULT_SERVER_BEAN_ID = "apiInitializeServerController";
	public static final String DEFAULT_SPRING_STARTUP_BEAN_ID = "springInitializer";

	private String serverInitializerBeanId = DEFAULT_SERVER_INITILIZER_BEAN_ID;
	private String serverBeanId = DEFAULT_SERVER_BEAN_ID;
	private String springInitializerBeanId = DEFAULT_SPRING_STARTUP_BEAN_ID;

	/**
	 * Whether or not to initialize the server automatically once the main
	 * context is online.
	 */
	private boolean automaticDevelopmentInitialization = true;

	public String getServerBeanId() {
		return this.serverBeanId;
	}

	public void setServerBeanId(String serverBeanId) {
		if (serverBeanId == null) {
			throw new IllegalArgumentException("serverBeanId cannot be null.");
		}

		this.serverBeanId = serverBeanId;
	}

	public String getServerInitializerBeanId() {
		return this.serverInitializerBeanId;
	}

	public void setServerInitializerBeanId(String serverInitializerBeanId) {
		if (serverInitializerBeanId == null) {
			throw new IllegalArgumentException("serverInitializerBeanId cannot be null.");
		}

		this.serverInitializerBeanId = serverInitializerBeanId;
	}

	public String getSpringInitializerBeanId() {
		return this.springInitializerBeanId;
	}

	public void setSpringInitializerBeanId(String springInitializerBeanId) {
		if (springInitializerBeanId == null) {
			throw new IllegalArgumentException("springInitializerBeanId cannot be null.");
		}

		this.springInitializerBeanId = springInitializerBeanId;
	}

	public boolean isAutomaticDevelopmentInitialization() {
		return this.automaticDevelopmentInitialization;
	}

	public void setAutomaticDevelopmentInitialization(boolean automaticDevelopmentInitialization) {
		this.automaticDevelopmentInitialization = automaticDevelopmentInitialization;
	}

	// MARK: AppServerInitializationConfigurer
	@Override
	public void configureContextInitializationComponents(AppConfiguration appConfig,
	                                                     SpringBeansXMLBuilder builder) {

		// Server Initializer
		this.configureServerInitializerComponent(appConfig, builder, this.serverInitializerBeanId);

		// Server Factory
		this.configureServerStartupComponent(appConfig, builder);
	}

	protected abstract void configureServerInitializerComponent(AppConfiguration appConfig,
	                                                            SpringBeansXMLBuilder builder,
	                                                            String serverInitializerBeanId);

	protected void configureServerStartupComponent(AppConfiguration appConfig,
	                                               SpringBeansXMLBuilder builder) {
		if (this.automaticDevelopmentInitialization) {
			builder.bean(this.springInitializerBeanId).beanClass(SpringInitializerImpl.class).c().bean()
			        .beanClass(GoogleAppEngineContextualFactoryImpl.class).property("developmentSource").bean()
			        .beanClass(SourceImpl.class).c().ref(this.serverInitializerBeanId);
		}
	}

	@Override
	public void configureServerInitializationComponents(AppConfiguration appConfig,
	                                                    SpringBeansXMLBuilder builder) {

		builder.comment("Initialize Server Controller");
		builder.bean(this.serverBeanId).beanClass(ApiInitializeServerController.class).c()
		        .ref(this.serverInitializerBeanId);

	}

}
