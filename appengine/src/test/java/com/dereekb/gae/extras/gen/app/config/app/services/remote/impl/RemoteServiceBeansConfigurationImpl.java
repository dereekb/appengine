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
	private String clientScheduleTaskServiceBeanId;
	private String clientModelRolesServiceBeanId;
	private String clientModelLinkServiceBeanId;

	public RemoteServiceBeansConfigurationImpl(AppServiceConfigurationInfo appServiceConfigurationInfo) {
		this(appServiceConfigurationInfo.getAppServiceName() + "Service");
	}

	public RemoteServiceBeansConfigurationImpl(String serviceBeanPrefix) {
		this.setServiceBeanPrefix(serviceBeanPrefix);

		this.setServiceSystemTokenFactoryBeanId(serviceBeanPrefix + "SystemTokenFactory");
		this.setClientRequestSenderBeanId(serviceBeanPrefix + "ClientRequestSender");
		this.setClientApiRequestSenderBeanId(serviceBeanPrefix + "ClientApiRequestSender");
		this.setSecuredClientApiRequestSenderBeanId(serviceBeanPrefix + "SecuredClientApiRequestSender");
		this.setClientScheduleTaskServiceBeanId(serviceBeanPrefix + "ScheduleTaskService");
		this.setClientModelRolesServiceBeanId(serviceBeanPrefix + "ClientModelRolesService");
		this.setClientModelLinkServiceBeanId(serviceBeanPrefix + "ClientLinkService");
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
	public String getClientScheduleTaskServiceBeanId() {
		return this.clientScheduleTaskServiceBeanId;
	}

	public void setClientScheduleTaskServiceBeanId(String clientScheduleTaskServiceBeanId) {
		if (clientScheduleTaskServiceBeanId == null) {
			throw new IllegalArgumentException("clientScheduleTaskServiceBeanId cannot be null.");
		}

		this.clientScheduleTaskServiceBeanId = clientScheduleTaskServiceBeanId;
	}

	@Override
	public String getClientModelRolesServiceBeanId() {
		return this.clientModelRolesServiceBeanId;
	}

	public void setClientModelRolesServiceBeanId(String clientModelRolesServiceBeanId) {
		if (clientModelRolesServiceBeanId == null) {
			throw new IllegalArgumentException("clientModelRolesServiceBeanId cannot be null.");
		}

		this.clientModelRolesServiceBeanId = clientModelRolesServiceBeanId;
	}

	@Override
	public String getClientModelLinkServiceBeanId() {
		return this.clientModelLinkServiceBeanId;
	}

	public void setClientModelLinkServiceBeanId(String clientModelLinkServiceBeanId) {
		if (clientModelLinkServiceBeanId == null) {
			throw new IllegalArgumentException("clientModelLinkServiceBeanId cannot be null.");
		}

		this.clientModelLinkServiceBeanId = clientModelLinkServiceBeanId;
	}

}
