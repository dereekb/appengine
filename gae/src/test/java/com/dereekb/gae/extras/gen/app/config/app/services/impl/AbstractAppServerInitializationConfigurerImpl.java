package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppServerInitializationConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.web.api.server.initialize.ApiInitializeServerController;

/**
 * Abstract {@link AppServerInitializationConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public abstract class AbstractAppServerInitializationConfigurerImpl
        implements AppServerInitializationConfigurer {

	public static final String DEFAULT_SERVER_BEAN_ID = "apiInitializeServerController";
	public static final String DEFAULT_DELEGATE_BEAN_ID = "apiInitializeServerControllerDelegate";

	private String serverBeanId = DEFAULT_SERVER_BEAN_ID;
	private String delegateBeanId = DEFAULT_DELEGATE_BEAN_ID;

	public String getServerBeanId() {
		return this.serverBeanId;
	}

	public void setServerBeanId(String serverBeanId) {
		if (serverBeanId == null) {
			throw new IllegalArgumentException("serverBeanId cannot be null.");
		}

		this.serverBeanId = serverBeanId;
	}

	public String getDelegateBeanId() {
		return this.delegateBeanId;
	}

	public void setDelegateBeanId(String delegateBeanId) {
		if (delegateBeanId == null) {
			throw new IllegalArgumentException("delegateBeanId cannot be null.");
		}

		this.delegateBeanId = delegateBeanId;
	}

	// MARK: AppServerInitializationConfigurer
	@Override
	public void configureServerInitializationComponents(AppConfiguration appConfig,
	                                                    SpringBeansXMLBuilder builder) {

		builder.comment("Initialize Server Controller");
		builder.bean(this.serverBeanId).beanClass(ApiInitializeServerController.class).c().ref(this.delegateBeanId);
		this.configureServerDelegateComponent(appConfig, builder);
	}

	protected abstract void configureServerDelegateComponent(AppConfiguration appConfig,
	                                                         SpringBeansXMLBuilder builder);

}
