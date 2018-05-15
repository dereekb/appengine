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

	private String serviceSystemTokenFactoryBeanId;
	private String clientRequestSenderBeanId;
	private String clientApiRequestSenderBeanId;
	private String securedClientApiRequestSenderBeanId;
	private String clientModelRolesContextServiceBeanId;

	public RemoteServiceBeansConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		this(appServiceConfigurationInfo.getAppServiceName() + "Service");
	}

	public RemoteServiceBeansConfigurationImpl(String serviceBeanPrefix) {
		this.setServiceBeanPrefix(serviceBeanPrefix);

		this.setServiceSystemTokenFactoryBeanId(serviceBeanPrefix + "SystemTokenFactory");
		this.setClientRequestSenderBeanId(serviceBeanPrefix + "ClientRequestSender");
		this.setClientApiRequestSenderBeanId(serviceBeanPrefix + "ClientApiRequestSender");
		this.setSecuredClientApiRequestSenderBeanId(serviceBeanPrefix + "SecuredClientApiRequestSender");
		this.setClientModelRolesContextServiceBeanId(serviceBeanPrefix + "ClientModelRolesContextService");
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
	public String getServiceSystemTokenFactoryBeanId() {
		return this.serviceSystemTokenFactoryBeanId;
	}

	public void setServiceSystemTokenFactoryBeanId(String serviceSystemTokenFactoryBeanId) {
		if (serviceSystemTokenFactoryBeanId == null) {
			throw new IllegalArgumentException("serviceSystemTokenFactoryBeanId cannot be null.");
		}

		this.serviceSystemTokenFactoryBeanId = serviceSystemTokenFactoryBeanId;
	}

	@Override
	public String getClientRequestSenderBeanId() {
		return this.clientRequestSenderBeanId;
	}

	public void setClientRequestSenderBeanId(String clientRequestSenderBeanId) {
		if (clientRequestSenderBeanId == null) {
			throw new IllegalArgumentException("clientRequestSenderBeanId cannot be null.");
		}

		this.clientRequestSenderBeanId = clientRequestSenderBeanId;
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

	@Override
	public String getSecuredClientApiRequestSenderBeanId() {
		return this.securedClientApiRequestSenderBeanId;
	}

	public void setSecuredClientApiRequestSenderBeanId(String securedClientApiRequestSenderBeanId) {
		if (securedClientApiRequestSenderBeanId == null) {
			throw new IllegalArgumentException("securedClientApiRequestSenderBeanId cannot be null.");
		}

		this.securedClientApiRequestSenderBeanId = securedClientApiRequestSenderBeanId;
	}

	@Override
	public String getClientModelRolesContextServiceBeanId() {
		return this.clientModelRolesContextServiceBeanId;
	}

	public void setClientModelRolesContextServiceBeanId(String clientModelRolesContextServiceBeanId) {
		if (clientModelRolesContextServiceBeanId == null) {
			throw new IllegalArgumentException("clientModelRolesContextServiceBeanId cannot be null.");
		}

		this.clientModelRolesContextServiceBeanId = clientModelRolesContextServiceBeanId;
	}

}
