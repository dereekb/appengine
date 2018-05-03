package com.dereekb.gae.extras.gen.app.config.app.services.remote.impl;

import com.dereekb.gae.extras.gen.app.config.app.AppServiceConfigurationInfo;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.AppRemoteServiceBeansConfiguration;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link AppRemoteServiceBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppRemoteServiceBeansConfigurationImpl
        implements AppRemoteServiceBeansConfiguration {

	private RemoteServiceContextAvailability contextAvailability = RemoteServiceContextAvailability.TASKQUEUE;

	private String serviceBeanPrefix;

	private String clientApiRequestSenderBeanId;

	public AppRemoteServiceBeansConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		this(appServiceConfigurationInfo.getAppServiceName() + "Service");
	}

	public AppRemoteServiceBeansConfigurationImpl(String serviceBeanPrefix) {
		this.setServiceBeanPrefix(serviceBeanPrefix);
		this.setClientApiRequestSenderBeanId(serviceBeanPrefix + "ClientApiRequestSender");
	}

	// MARK: AppRemoteServiceBeansConfiguration
	@Override
	public RemoteServiceContextAvailability getContextAvailability() {
		return this.contextAvailability;
	}

	public void setContextAvailability(RemoteServiceContextAvailability contextAvailability) {
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
