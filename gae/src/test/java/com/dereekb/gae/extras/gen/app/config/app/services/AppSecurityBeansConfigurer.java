package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationService;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;

/**
 * Used for configuring a user token bean.
 *
 * @author dereekb
 *
 */
public interface AppSecurityBeansConfigurer
        extends SystemLoginTokenFactoryConfigurer {

	// Beans
	public String getLoginTokenSignatureFactoryBeanId();

	public String getRefreshTokenSignatureFactoryBeanId();

	/**
	 * Bean id of the {@link ClientLoginTokenValidationService}.
	 * <p>
	 * Useful only for microservices that have a remote login/auth system.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getClientLoginTokenValidationServiceBeanId();

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
