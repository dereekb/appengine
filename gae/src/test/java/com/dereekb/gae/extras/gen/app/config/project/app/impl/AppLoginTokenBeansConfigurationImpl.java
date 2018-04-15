package com.dereekb.gae.extras.gen.app.config.project.app.impl;

import com.dereekb.gae.extras.gen.app.config.project.app.AppLoginTokenBeansConfiguration;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenBuilderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.provider.details.impl.LoginTokenUserDetailsBuilderImpl;
import com.dereekb.gae.server.auth.security.token.provider.impl.LoginTokenAuthenticationProviderImpl;

/**
 * {@link AppLoginTokenBeansConfiguration} implementation.
 *
 * @author dereekb
 *
 */
public class AppLoginTokenBeansConfigurationImpl
        implements AppLoginTokenBeansConfiguration {

	private Class<?> loginTokenEncoderDecoderClass = LoginTokenEncoderDecoderImpl.class;
	private Class<?> loginTokenBuilderClass = LoginTokenBuilderImpl.class;
	private Class<?> loginTokenAuthenticationProviderClass = LoginTokenAuthenticationProviderImpl.class;
	private Class<?> loginTokenUserDetailsBuilderClass = LoginTokenUserDetailsBuilderImpl.class;

	@Override
	public Class<?> getLoginTokenEncoderDecoderClass() {
		return this.loginTokenEncoderDecoderClass;
	}

	public void setLoginTokenEncoderDecoderClass(Class<?> loginTokenEncoderDecoderClass) {
		if (loginTokenEncoderDecoderClass == null) {
			throw new IllegalArgumentException("loginTokenEncoderDecoderClass cannot be null.");
		}

		this.loginTokenEncoderDecoderClass = loginTokenEncoderDecoderClass;
	}

	@Override
	public Class<?> getLoginTokenBuilderClass() {
		return this.loginTokenBuilderClass;
	}

	public void setLoginTokenBuilderClass(Class<?> loginTokenBuilderClass) {
		if (loginTokenBuilderClass == null) {
			throw new IllegalArgumentException("loginTokenBuilderClass cannot be null.");
		}

		this.loginTokenBuilderClass = loginTokenBuilderClass;
	}

	@Override
	public Class<?> getLoginTokenAuthenticationProviderClass() {
		return this.loginTokenAuthenticationProviderClass;
	}

	public void setLoginTokenAuthenticationProviderClass(Class<?> loginTokenAuthenticationProviderClass) {
		if (loginTokenAuthenticationProviderClass == null) {
			throw new IllegalArgumentException("loginTokenAuthenticationProviderClass cannot be null.");
		}

		this.loginTokenAuthenticationProviderClass = loginTokenAuthenticationProviderClass;
	}

	@Override
	public Class<?> getLoginTokenUserDetailsBuilderClass() {
		return this.loginTokenUserDetailsBuilderClass;
	}

	public void setLoginTokenUserDetailsBuilderClass(Class<?> loginTokenUserDetailsBuilderClass) {
		if (loginTokenUserDetailsBuilderClass == null) {
			throw new IllegalArgumentException("loginTokenUserDetailsBuilderClass cannot be null.");
		}

		this.loginTokenUserDetailsBuilderClass = loginTokenUserDetailsBuilderClass;
	}

}
