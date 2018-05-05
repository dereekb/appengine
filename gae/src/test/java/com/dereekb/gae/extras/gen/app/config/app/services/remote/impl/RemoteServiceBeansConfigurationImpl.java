package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.RemoteServiceBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link RemoteServiceBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class RemoteServiceBeansConfigurationImpl
        implements RemoteServiceBeansConfiguration {

	private AppSpringContextType contextAvailability = AppSpringContextType.TASKQUEUE;

	private String serviceBeanPrefix;

	private String clientApiRequestSenderBeanId;

	public RemoteServiceBeansConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		this(appServiceConfigurationInfo.getAppServiceName() + "Service");
	}

	public RemoteServiceBeansConfigurationImpl(String serviceBeanPrefix) {
		this.setServiceBeanPrefix(serviceBeanPrefix);
		this.setClientApiRequestSenderBeanId(serviceBeanPrefix + "ClientApiRequestSender");
	}

	// MARK: AppRemoteServiceBeansConfiguration
	@Override
	public AppSpringContextType getContextAvailability() {
		return this.contextAvailability;
	}

	public void setContextAvailability(AppSpringContextType contextAvailability) {
		if (contextAvailability == null) {
			throw new IllegalArgumentException("contextAvailability cannot be null.");
		}

		this.contextAvailability = contextAvailability;
	}

	@Override
	public String getServiceBeanPrefix() {
		return this.serviceBeanPrefix;
	}

	public void setServiceBeanPrefix(String serviceBeanPrefix) {
		if (StringUtility.isEmptyString(serviceBeanPrefix)) {
			throw new IllegalArgumentException("serviceBeanPrefix cannot be null or empty.");
		}

		this.serviceBeanPrefix = serviceBeanPrefix;
	}

	@Override
	public String getClientApiRequestSenderBeanId() {
		return this.clientApiRequestSenderBeanId;
	}

	public void setClientApiRequestSenderBeanId(String clientApiRequestSenderBeanId) {
		if (clientApiRequestSenderBeanId == null) {
			throw new IllegalArgumentException("clientApiRequestSenderBeanId cannot be null.");
		}

		this.clientApiRequestSenderBeanId = clientApiRequestSenderBeanId;
	}

}
