package com.dereekb.gae.extras.gen.app.config.app.model.remote;

import com.dereekb.gae.extras.gen.app.config.app.model.shared.AppModelBeansConfiguration;

/**
 * {@link AppModelBeansConfiguration} extension for remote beans.
 *
 * @author dereekb
 *
 */
public interface RemoteModelBeansConfiguration
        extends AppModelBeansConfiguration {

	public String getModelClientCrudServiceBeanId();

	public String getModelClientCreateServiceBeanId();

	public String getModelClientReadServiceBeanId();

	public String getModelClientUpdateServiceBeanId();

	public String getModelClientDeleteServiceBeanId();

	public String getModelClientQueryServiceBeanId();

}
