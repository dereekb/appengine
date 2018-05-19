package com.dereekb.gae.extras.gen.app.config.app.services.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppEventServiceListenersConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link AppEventServiceListenersConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class AppEventServiceListenersConfigurerImpl
        implements AppEventServiceListenersConfigurer {

	private List<AppEventServiceListenersConfigurer> configurers;

	public AppEventServiceListenersConfigurerImpl() {
		this(Collections.emptyList());
	}

	public AppEventServiceListenersConfigurerImpl(List<AppEventServiceListenersConfigurer> configurers) {
		this.setConfigurers(configurers);
	}

	public List<AppEventServiceListenersConfigurer> getConfigurers() {
		return this.configurers;
	}

	public void setConfigurers(List<AppEventServiceListenersConfigurer> configurers) {
		if (configurers == null) {
			throw new IllegalArgumentException("configurers cannot be null.");
		}

		this.configurers = configurers;
	}

	// MARK: AppEventServiceListenersConfigurer
	@Override
	public List<String> configureEventServiceListeners(AppConfiguration appConfiguration,
	                                                   SpringBeansXMLBuilder builder) {

		List<String> beanIds = new ArrayList<String>();

		for (AppEventServiceListenersConfigurer configurer : this.configurers) {
			beanIds.addAll(configurer.configureEventServiceListeners(appConfiguration, builder));
		}

		return beanIds;
	}

	@Override
	public String toString() {
		return "AppEventServiceListenersConfigurerImpl [configurers=" + this.configurers + "]";
	}

}
