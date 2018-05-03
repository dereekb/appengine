package com.dereekb.gae.extras.gen.app.config.app.services.remote;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.remote.impl.RemoteServiceContextAvailability;

/**
 * Similar to {@link LocalModelBeansConfiguration}, but with common remote service
 * components/beans.
 *
 * @author dereekb
 *
 */
public interface AppRemoteServiceBeansConfiguration {

	/**
	 * The context availability of the beans within this configuration.
	 *
	 * @return {@link RemoteServiceContextAvailability}. Never {@code null}.
	 */
	public RemoteServiceContextAvailability getContextAvailability();

	public String getServiceBeanPrefix();

	public String getClientApiRequestSenderBeanId();

}
