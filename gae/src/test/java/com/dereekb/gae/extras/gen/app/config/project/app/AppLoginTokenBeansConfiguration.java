package com.dereekb.gae.extras.gen.app.config.project.app;

import com.dereekb.gae.extras.gen.app.config.model.AppSecurityBeansConfigurer;

/**
 * @author dereekb
 *
 * @deprecated Use {@link AppSecurityBeansConfigurer} instead.
 */
@Deprecated
public interface AppLoginTokenBeansConfiguration {

	public Class<?> getLoginTokenAuthenticationProviderClass();

	public Class<?> getLoginTokenUserDetailsBuilderClass();

	public Class<?> getLoginTokenEncoderDecoderClass();

	public Class<?> getLoginTokenBuilderClass();

}
