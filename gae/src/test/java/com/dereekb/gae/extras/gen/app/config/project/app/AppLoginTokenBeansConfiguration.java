package com.dereekb.gae.extras.gen.app.config.project.app;


public interface AppLoginTokenBeansConfiguration {

	public Class<?> getLoginTokenAuthenticationProviderClass();

	public Class<?> getLoginTokenUserDetailsBuilderClass();

	public Class<?> getLoginTokenEncoderDecoderClass();

	public Class<?> getLoginTokenBuilderClass();

}
