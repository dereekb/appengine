package com.dereekb.gae.extras.gen.app.config.app.services.remote;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.utility.AppSpringContextType;

/**
 * Similar to {@link LocalModelBeansConfiguration}, but with common remote
 * service components/beans.
 *
 * @author dereekb
 *
 */
public interface RemoteServiceBeansConfiguration {

	/**
	 * The context availability of the beans within this configuration.
	 *
	 * @return {@link RemoteServiceContextAvailability}. Never {@code null}.
	 */
	public AppSpringContextType getContextAvailability();

	public String getServiceBeanPrefix();

	public String getServiceSystemTokenFactoryBeanId();

	public String getClientRequestSenderBeanId();

	public String getClientApiRequestSenderBeanId();

	public String getSecuredClientApiRequestSenderBeanId();

	public String getClientModelRolesContextServiceBeanId();

}
