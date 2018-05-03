package com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.AppRemoteServiceConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.configurer.AppRemoteServiceContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppRemoteServiceConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppRemoteServiceConfigurerImpl
        implements AppRemoteServiceConfigurer {

	private AppRemoteServiceContextConfigurer sharedContextConfigurer;
	private AppRemoteServiceContextConfigurer taskQueueContextConfigurer;

	public AppRemoteServiceConfigurerImpl() {
		this(NoOpAppRemoteServiceContextConfigurerImpl.make(), NoOpAppRemoteServiceContextConfigurerImpl.make());
	}

	public AppRemoteServiceConfigurerImpl(AppRemoteServiceContextConfigurer sharedContextConfigurer,
	        AppRemoteServiceContextConfigurer taskQueueContextConfigurer) {
		super();
		this.setSharedContextConfigurer(sharedContextConfigurer);
		this.setTaskQueueContextConfigurer(taskQueueContextConfigurer);
	}

	public AppRemoteServiceContextConfigurer getSharedContextConfigurer() {
		return this.sharedContextConfigurer;
	}

	public void setSharedContextConfigurer(AppRemoteServiceContextConfigurer sharedContextConfigurer) {
		if (sharedContextConfigurer == null) {
			throw new IllegalArgumentException("sharedContextConfigurer cannot be null.");
		}

		this.sharedContextConfigurer = sharedContextConfigurer;
	}

	public AppRemoteServiceContextConfigurer getTaskQueueContextConfigurer() {
		return this.taskQueueContextConfigurer;
	}

	public void setTaskQueueContextConfigurer(AppRemoteServiceContextConfigurer taskQueueContextConfigurer) {
		if (taskQueueContextConfigurer == null) {
			throw new IllegalArgumentException("taskQueueContextConfigurer cannot be null.");
		}

		this.taskQueueContextConfigurer = taskQueueContextConfigurer;
	}

	// MARK: AppRemoteServiceConfigurer
	@Override
	public void configureSharedContextComponents(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder) {
		this.sharedContextConfigurer.configureContextComponents(appConfig, builder);
	}

	@Override
	public void configureTaskQueueContextComponents(AppConfiguration appConfig,
	                                                SpringBeansXMLBuilder builder) {
		this.taskQueueContextConfigurer.configureContextComponents(appConfig, builder);
	}

	@Override
	public String toString() {
		return "AppRemoteServiceConfigurerImpl [sharedContextConfigurer=" + this.sharedContextConfigurer
		        + ", taskQueueContextConfigurer=" + this.taskQueueContextConfigurer + "]";
	}

}
