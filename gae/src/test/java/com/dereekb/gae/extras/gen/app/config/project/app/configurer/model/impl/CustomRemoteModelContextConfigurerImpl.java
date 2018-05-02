package com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.AppModelConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomRemoteModelContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomRemoteModelSharedContextConfigurer;
import com.dereekb.gae.extras.gen.app.config.project.app.configurer.model.CustomRemoteModelTaskQueueContextConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

/**
 * {@link CustomRemoteModelContextConfigurer} implementation for remote-only
 * models.
 *
 * @author dereekb
 *
 */
public class CustomRemoteModelContextConfigurerImpl extends AbstractCustomModelContextConfigurer {

	private CustomRemoteModelSharedContextConfigurer customRemoteModelSharedContextConfigurer;
	private CustomRemoteModelTaskQueueContextConfigurer customRemoteModelTaskQueueContextConfigurer;

	public CustomRemoteModelSharedContextConfigurer getCustomRemoteModelSharedContextConfigurer() {
		return this.customRemoteModelSharedContextConfigurer;
	}

	public void setCustomRemoteModelSharedContextConfigurer(CustomRemoteModelSharedContextConfigurer customRemoteModelSharedContextConfigurer) {
		if (customRemoteModelSharedContextConfigurer == null) {
			throw new IllegalArgumentException("customRemoteModelSharedContextConfigurer cannot be null.");
		}

		this.customRemoteModelSharedContextConfigurer = customRemoteModelSharedContextConfigurer;
	}

	public CustomRemoteModelTaskQueueContextConfigurer getCustomRemoteModelTaskQueueContextConfigurer() {
		return this.customRemoteModelTaskQueueContextConfigurer;
	}

	public void setCustomRemoteModelTaskQueueContextConfigurer(CustomRemoteModelTaskQueueContextConfigurer customRemoteModelTaskQueueContextConfigurer) {
		if (customRemoteModelTaskQueueContextConfigurer == null) {
			throw new IllegalArgumentException("customRemoteModelTaskQueueContextConfigurer cannot be null.");
		}

		this.customRemoteModelTaskQueueContextConfigurer = customRemoteModelTaskQueueContextConfigurer;
	}

	// MARK: CustomRemoteModelContextConfigurer
	@Override
	public void configureRemoteModelSharedContextComponents(AppConfiguration appConfig,
	                                                        AppModelConfiguration modelConfig,
	                                                        SpringBeansXMLBuilder builder) {
		if (this.customRemoteModelSharedContextConfigurer != null) {
			this.customRemoteModelSharedContextConfigurer.configureRemoteModelSharedContextComponents(appConfig,
			        modelConfig, builder);
		}
	}

	@Override
	public void configureRemoteModelTaskQueueContextComponents(AppConfiguration appConfig,
	                                                           AppModelConfiguration modelConfig,
	                                                           SpringBeansXMLBuilder builder) {
		if (this.customRemoteModelTaskQueueContextConfigurer != null) {
			this.customRemoteModelTaskQueueContextConfigurer.configureRemoteModelTaskQueueContextComponents(appConfig,
			        modelConfig, builder);
		}
	}

	@Override
	public String toString() {
		return "CustomRemoteModelContextConfigurerImpl [customRemoteModelSharedContextConfigurer="
		        + this.customRemoteModelSharedContextConfigurer + ", customRemoteModelTaskQueueContextConfigurer="
		        + this.customRemoteModelTaskQueueContextConfigurer + "]";
	}

}
