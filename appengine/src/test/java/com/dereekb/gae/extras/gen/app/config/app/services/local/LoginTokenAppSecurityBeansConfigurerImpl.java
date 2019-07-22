package com.dereekb.gae.extras.gen.app.config.app.services.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.SystemLoginTokenFactoryConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.local.impl.LocalSystemLoginTokenFactoryConfigurerImpl;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLMapBuilder;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.impl.NewLoginGeneratorImpl;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthClientConfig;
import com.dereekb.gae.server.auth.security.login.oauth.impl.OAuthClientConfigImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.facebook.FacebookOAuthService;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.scribe.google.GoogleOAuthService;
import com.dereekb.gae.server.auth.security.model.query.task.impl.LoginSecurityModelQueryTaskOverrideImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenBuilderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.provider.details.impl.LoginTokenUserDetailsBuilderImpl;
import com.dereekb.gae.server.auth.security.token.provider.impl.LoginTokenAuthenticationProviderImpl;

/**
 * Default {@link AppSecurityBeansConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenAppSecurityBeansConfigurerImpl
        implements AppSecurityBeansConfigurer {

	private String loginTokenSignatureFactoryBeanId = "loginTokenSignatureFactory";
	private String refreshTokenSignatureFactoryBeanId = "refreshTokenSignatureFactory";
	private String clientLoginTokenValidationServiceBeanId = "clientLoginTokenValidationService";

	private OAuthClientConfig googleOAuthConfig;
	private OAuthClientConfig facebookOAuthConfig;

	private Class<?> loginTokenEncoderDecoderClass = LoginTokenEncoderDecoderImpl.class;
	private Class<?> loginTokenBuilderClass = LoginTokenBuilderImpl.class;
	private Class<?> loginTokenAuthenticationProviderClass = LoginTokenAuthenticationProviderImpl.class;
	private Class<?> loginTokenUserDetailsBuilderClass = LoginTokenUserDetailsBuilderImpl.class;

	private Class<?> loginSecurityModelQueryTaskOverrideClass = LoginSecurityModelQueryTaskOverrideImpl.class;

	private SystemLoginTokenFactoryConfigurer systemLoginTokenFactoryConfigurer;

	public LoginTokenAppSecurityBeansConfigurerImpl() {
		this(new LocalSystemLoginTokenFactoryConfigurerImpl());
	}

	public LoginTokenAppSecurityBeansConfigurerImpl(
	        SystemLoginTokenFactoryConfigurer systemLoginTokenFactoryConfigurer) {
		this.setSystemLoginTokenFactoryConfigurer(systemLoginTokenFactoryConfigurer);
		this.initializeDefaultClasses();
	}

	protected void initializeDefaultClasses() {
		this.setLoginTokenEncoderDecoderClass(LoginTokenEncoderDecoderImpl.class);
		this.setLoginTokenBuilderClass(LoginTokenBuilderImpl.class);
		this.setLoginTokenAuthenticationProviderClass(LoginTokenAuthenticationProviderImpl.class);
		this.setLoginTokenUserDetailsBuilderClass(LoginTokenUserDetailsBuilderImpl.class);
		this.setLoginSecurityModelQueryTaskOverrideClass(LoginSecurityModelQueryTaskOverrideImpl.class);
	}

	@Override
	public String getLoginTokenSignatureFactoryBeanId() {
		return this.loginTokenSignatureFactoryBeanId;
	}

	public void setLoginTokenSignatureFactoryBeanId(String loginTokenSignatureFactoryBeanId) {
		if (loginTokenSignatureFactoryBeanId == null) {
			throw new IllegalArgumentException("loginTokenSignatureFactoryBeanId cannot be null.");
		}

		this.loginTokenSignatureFactoryBeanId = loginTokenSignatureFactoryBeanId;
	}

	@Override
	public String getRefreshTokenSignatureFactoryBeanId() {
		return this.refreshTokenSignatureFactoryBeanId;
	}

	public void setRefreshTokenSignatureFactoryBeanId(String refreshTokenSignatureFactoryBeanId) {
		if (refreshTokenSignatureFactoryBeanId == null) {
			throw new IllegalArgumentException("refreshTokenSignatureFactoryBeanId cannot be null.");
		}

		this.refreshTokenSignatureFactoryBeanId = refreshTokenSignatureFactoryBeanId;
	}

	@Override
	public String getClientLoginTokenValidationServiceBeanId() {
		return this.clientLoginTokenValidationServiceBeanId;
	}

	public void setClientLoginTokenValidationServiceBeanId(String clientLoginTokenValidationServiceBeanId) {
		if (clientLoginTokenValidationServiceBeanId == null) {
			throw new IllegalArgumentException("clientLoginTokenValidationServiceBeanId cannot be null.");
		}

		this.clientLoginTokenValidationServiceBeanId = clientLoginTokenValidationServiceBeanId;
	}

	public OAuthClientConfig getGoogleOAuthConfig() {
		return this.googleOAuthConfig;
	}

	public void setGoogleOAuthConfig(OAuthClientConfig googleOAuthConfig) {
		if (googleOAuthConfig == null) {
			throw new IllegalArgumentException("googleOAuthConfig cannot be null.");
		}

		this.googleOAuthConfig = googleOAuthConfig;
	}

	public OAuthClientConfig getFacebookOAuthConfig() {
		return this.facebookOAuthConfig;
	}

	public void setFacebookOAuthConfig(OAuthClientConfig facebookOAuthConfig) {
		if (facebookOAuthConfig == null) {
			throw new IllegalArgumentException("facebookOAuthConfig cannot be null.");
		}

		this.facebookOAuthConfig = facebookOAuthConfig;
	}

	public Class<?> getLoginTokenEncoderDecoderClass() {
		return this.loginTokenEncoderDecoderClass;
	}

	public void setLoginTokenEncoderDecoderClass(Class<?> loginTokenEncoderDecoderClass) {
		if (loginTokenEncoderDecoderClass == null) {
			throw new IllegalArgumentException("loginTokenEncoderDecoderClass cannot be null.");
		}

		this.loginTokenEncoderDecoderClass = loginTokenEncoderDecoderClass;
	}

	public Class<?> getLoginTokenBuilderClass() {
		return this.loginTokenBuilderClass;
	}

	public void setLoginTokenBuilderClass(Class<?> loginTokenBuilderClass) {
		if (loginTokenBuilderClass == null) {
			throw new IllegalArgumentException("loginTokenBuilderClass cannot be null.");
		}

		this.loginTokenBuilderClass = loginTokenBuilderClass;
	}

	public Class<?> getLoginTokenAuthenticationProviderClass() {
		return this.loginTokenAuthenticationProviderClass;
	}

	public void setLoginTokenAuthenticationProviderClass(Class<?> loginTokenAuthenticationProviderClass) {
		if (loginTokenAuthenticationProviderClass == null) {
			throw new IllegalArgumentException("loginTokenAuthenticationProviderClass cannot be null.");
		}

		this.loginTokenAuthenticationProviderClass = loginTokenAuthenticationProviderClass;
	}

	public Class<?> getLoginTokenUserDetailsBuilderClass() {
		return this.loginTokenUserDetailsBuilderClass;
	}

	public void setLoginTokenUserDetailsBuilderClass(Class<?> loginTokenUserDetailsBuilderClass) {
		if (loginTokenUserDetailsBuilderClass == null) {
			throw new IllegalArgumentException("loginTokenUserDetailsBuilderClass cannot be null.");
		}

		this.loginTokenUserDetailsBuilderClass = loginTokenUserDetailsBuilderClass;
	}

	public SystemLoginTokenFactoryConfigurer getSystemLoginTokenFactoryConfigurer() {
		return this.systemLoginTokenFactoryConfigurer;
	}

	public void setSystemLoginTokenFactoryConfigurer(SystemLoginTokenFactoryConfigurer systemLoginTokenFactoryConfigurer) {
		this.systemLoginTokenFactoryConfigurer = systemLoginTokenFactoryConfigurer;
	}

	// MARK: AppSecurityBeansConfigurer
	@Override
	public Class<?> getLoginSecurityModelQueryTaskOverrideClass() {
		return this.loginSecurityModelQueryTaskOverrideClass;
	}

	public void setLoginSecurityModelQueryTaskOverrideClass(Class<?> loginSecurityModelQueryTaskOverrideClass) {
		if (loginSecurityModelQueryTaskOverrideClass == null) {
			throw new IllegalArgumentException("loginSecurityModelQueryTaskOverrideClass cannot be null.");
		}

		this.loginSecurityModelQueryTaskOverrideClass = loginSecurityModelQueryTaskOverrideClass;
	}

	@Override
	public void configureTokenEncoderDecoder(AppConfiguration appConfig,
	                                         SpringBeansXMLBeanBuilder<?> beanBuilder) {
		beanBuilder.beanClass(this.loginTokenEncoderDecoderClass).c().bean()
		        .factoryBean(this.loginTokenSignatureFactoryBeanId).factoryMethod("make");
	}

	@Override
	public void configureTokenBuilder(AppConfiguration appConfig,
	                                  SpringBeansXMLBeanBuilder<?> beanBuilder) {
		beanBuilder.beanClass(this.loginTokenBuilderClass).c().ref("loginGetter");
	}

	@Override
	public void configureTokenAuthenticationProvider(AppConfiguration appConfig,
	                                                 SpringBeansXMLBeanBuilder<?> beanBuilder) {
		beanBuilder.beanClass(this.loginTokenAuthenticationProviderClass).c()
		        .ref(appConfig.getAppBeans().getUtilityBeans().getLoginTokenUserDetailsBuilderBeanId());
	}

	@Override
	public void configureTokenUserDetailsBuilder(AppConfiguration appConfig,
	                                             SpringBeansXMLBeanBuilder<?> beanBuilder) {
		beanBuilder.beanClass(this.loginTokenUserDetailsBuilderClass).c().ref("loginTokenModelContextSetDencoder")
		        .ref("loginTokenGrantedAuthorityBuilder").ref("loginGetter").ref("loginPointerGetter");
	}

	@Override
	public void configureSystemLoginTokenFactory(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder beanBuilder) {
		SystemLoginTokenFactoryConfigurer configurer = this.systemLoginTokenFactoryConfigurer;
		configurer.configureSystemLoginTokenFactory(appConfig, beanBuilder);
	}

	@Override
	public void configureNewLoginGenerator(AppConfiguration appConfig,
	                                       SpringBeansXMLBuilder builder,
	                                       String newLoginGeneratorId) {
		builder.bean(newLoginGeneratorId).beanClass(NewLoginGeneratorImpl.class).c().ref("loginRegistry")
		        .ref("loginScheduleCreateReview");
	}

	@Override
	public void configureOAuthServiceManagerMap(AppConfiguration appConfig,
	                                            SpringBeansXMLBuilder builder,
	                                            String oAuthLoginServiceMapId) {

		SpringBeansXMLMapBuilder<SpringBeansXMLBuilder> map = builder.map(oAuthLoginServiceMapId);

		if (this.facebookOAuthConfig != null) {
			String facebookOAuthPointerTypeBeanId = "facebookOAuthPointerType";
			String facebookOAuthServiceBeanId = "facebookOAuthService";
			String facebookOAuthConfigBeanId = "facebookOAuthConfig";

			builder.bean(facebookOAuthConfigBeanId).beanClass(OAuthClientConfigImpl.class).c()
			        .value(this.facebookOAuthConfig.getClientId()).value(this.facebookOAuthConfig.getClientSecret());

			builder.bean(facebookOAuthServiceBeanId).beanClass(FacebookOAuthService.class).c()
			        .ref(facebookOAuthConfigBeanId);

			builder.enumBean(facebookOAuthPointerTypeBeanId, LoginPointerType.OAUTH_FACEBOOK);

			map.keyRefValueRefEntry(facebookOAuthPointerTypeBeanId, facebookOAuthServiceBeanId);
		}

		if (this.googleOAuthConfig != null) {
			String googleOAuthPointerTypeBeanId = "googleOAuthPointerType";
			String googleOAuthServiceBeanId = "googleOAuthService";
			String googleOAuthConfigBeanId = "googleOAuthConfig";

			builder.bean(googleOAuthConfigBeanId).beanClass(OAuthClientConfigImpl.class).c()
			        .value(this.googleOAuthConfig.getClientId()).value(this.googleOAuthConfig.getClientSecret());

			builder.bean(googleOAuthServiceBeanId).beanClass(GoogleOAuthService.class).c().ref(googleOAuthConfigBeanId);

			builder.enumBean(googleOAuthPointerTypeBeanId, LoginPointerType.OAUTH_GOOGLE);

			map.keyRefValueRefEntry(googleOAuthPointerTypeBeanId, googleOAuthServiceBeanId);
		}

	}

}
