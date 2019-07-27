package com.dereekb.gae.extras.gen.app.config.app.services;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationService;
import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.project.test.TestConfigurationGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;

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

	/**
	 * Configues the token builder.
	 *
	 * @param appConfig
	 * @param beanBuilder
	 * @param forTests whether or not this is being used in a testing context only. Is true when by the {@link TestConfigurationGenerator} for remote apps.
	 */
	public void configureTokenBuilder(AppConfiguration appConfig,
	                                  SpringBeansXMLBeanBuilder<?> beanBuilder,
	                                  boolean forTests);

	// Security
	public void configureTokenAuthenticationProvider(AppConfiguration appConfig,
	                                                 SpringBeansXMLBeanBuilder<?> beanBuilder);

	public void configureTokenUserDetailsBuilder(AppConfiguration appConfig,
	                                             SpringBeansXMLBeanBuilder<?> beanBuilder);

	public void configureOAuthServiceManagerMap(AppConfiguration appConfig,
	                                            SpringBeansXMLBuilder builder,
	                                            String oAuthLoginServiceMapId);

	public void configureNewLoginGenerator(AppConfiguration appConfig,
	                                       SpringBeansXMLBuilder builder,
	                                       String newLoginGeneratorId);

	public void configureTestRemoteLoginSystemLoginTokenContext(AppConfiguration appConfig,
	                                                            SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> loginTokenContextBuilder);

}
