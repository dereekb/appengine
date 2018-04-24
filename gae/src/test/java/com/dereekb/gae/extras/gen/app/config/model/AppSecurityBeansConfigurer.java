package com.dereekb.gae.extras.gen.app.config.model;

import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;

/**
 * Used for configuring a user token bean.
 *
 * @author dereekb
 *
 */
public interface AppSecurityBeansConfigurer {

	// Beans
	public String getLoginTokenSignatureFactoryBeanId();

	public String getRefreshTokenSignatureFactoryBeanId();

	// Models
	public Class<?> getLoginSecurityModelQueryTaskOverrideClass();

	// Login
	public void configureTokenEncoderDecoder(AppConfiguration appConfig,
	                                         SpringBeansXMLBeanBuilder<?> beanBuilder);

	public void configureTokenBuilder(AppConfiguration appConfig,
	                                  SpringBeansXMLBeanBuilder<?> beanBuilder);

	// Security
	public void configureTokenAuthenticationProvider(AppConfiguration appConfig,
	                                                 SpringBeansXMLBeanBuilder<?> beanBuilder);

	public void configureTokenUserDetailsBuilder(AppConfiguration appConfig,
	                                             SpringBeansXMLBeanBuilder<?> beanBuilder);

}
