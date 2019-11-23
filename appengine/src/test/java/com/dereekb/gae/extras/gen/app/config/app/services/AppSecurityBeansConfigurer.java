package com.dereekb.gae.extras.gen.app.config.app.services;

import java.util.List;

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
	/**
	 * Returns a list of additional secure paths for the security context to
	 * allow/protect.
	 *
	 * @return {@link List} or {@code null} if none.
	 */
	public List<String> getAdditionalSecureModelResources();

	public Class<?> getLoginSecurityModelQueryTaskOverrideClass();

	// Login
	/**
	 * Configues the token encoder decoder.
	 *
	 * @param appConfig
	 * @param beanBuilder
	 * @param forTests
	 *            whether or not this is being used in a testing context only.
	 *            Is true when by the {@link TestConfigurationGenerator} for
	 *            remote apps.
	 */
	public void configureTokenEncoderDecoder(AppConfiguration appConfig,
	                                         SpringBeansXMLBeanBuilder<?> beanBuilder,
	                                         boolean forTests);

	/**
	 * Configues the token builder.
	 *
	 * @param appConfig
	 * @param beanBuilder
	 * @param forTests
	 *            whether or not this is being used in a testing context only.
	 *            Is true when by the {@link TestConfigurationGenerator} for
	 *            remote apps.
	 */
	public void configureTokenBuilder(AppConfiguration appConfig,
	                                  SpringBeansXMLBeanBuilder<?> beanBuilder,
	                                  boolean forTests);

	// Security
	/**
	 * Configures the login token signature factory and refresh token signature
	 * factory for the app.
	 */
	public void configureTokenSignatureFactories(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder);

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
