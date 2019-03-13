package com.dereekb.gae.extras.gen.app.config.app.model.shared;

import com.dereekb.gae.extras.gen.app.config.app.model.local.LocalModelBeansConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.model.remote.RemoteModelBeansConfiguration;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextServiceEntry;

/**
 * Basic app model beans configuration.
 *
 * @author dereekb
 *
 * @see {@link LocalModelBeansConfiguration}.
 * @see {@link RemoteModelBeansConfiguration}.
 */
public interface AppModelBeansConfiguration {

	/**
	 * Returns the bean model name/prefix, which is usually
	 * {{@link #getModelType()} with the first character to lowercase.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getModelBeanPrefix();

	public String getModelTypeBeanId();

	public String getModelIdTypeBeanId();

	public String getModelClassBeanId();

	public String getModelDtoClassBeanId();

	public String getModelDtoBeanId();

	public String getModelDataConverterBeanId();

	public String getModelGetterBeanId();

	/**
	 * Generally returns the registry, but for remote models it will return the
	 * factory.
	 */
	public String getModelKeyListAccessorFactoryId();

	/**
	 * @see LoginTokenModelContextServiceEntry
	 */
	public String getModelSecurityContextServiceEntryBeanId();

}
