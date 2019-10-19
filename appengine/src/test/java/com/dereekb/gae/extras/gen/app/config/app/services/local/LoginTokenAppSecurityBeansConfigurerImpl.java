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
import com.dereekb.gae.server.auth.security.token.gae.SignatureConfigurationFactory;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenBuilderImpl;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenEncoderDecoderImpl;
import com.dereekb.gae.server.auth.security.token.provider.details.impl.LoginTokenUserDetailsBuilderImpl;
import com.dereekb.gae.server.auth.security.token.provider.impl.LoginTokenAuthenticationProviderImpl;
import com.dereekb.gae.test.server.auth.impl.TestRemoteLoginSystemLoginTokenContextImpl;
import com.dereekb.gae.utilities.misc.env.EnvStringUtility;

/**
 * Default {@link AppSecurityBeansConfigurer} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenAppSecurityBeansConfigurerImpl
        implements AppSecurityBeansConfigurer {

	public static final String PROD_LOGIN_TOKEN_SIGNATURE_SECRET_ENV_VAR = "LOGIN_TOKEN_SIGNATURE_SECRET";
	public static final String PROD_REFRESH_TOKEN_SIGNATURE_SECRET_ENV_VAR = "REFRESH_TOKEN_SIGNATURE_SECRET";

	public static final String SIGNATURE_SECRET_NOT_SET_VALUE = "SIGNATURE_NOT_SET";

	public static final String TEST_LOGIN_TOKEN_BUILDER_LOGIN_GETTER_BEAN_ID = "testLoginTokenBuilderLoginGetter";
	public static final String TEST_LOGIN_TOKEN_SIGNATURE_FACTORY_BEAN_ID = "testLoginTokenSignatureFactory";

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
	private Class<?> testRemoteLoginSystemLoginTokenContextClass = TestRemoteLoginSystemLoginTokenContextImpl.class;

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
		this.setTestRemoteLoginSystemLoginTokenContextClass(TestRemoteLoginSystemLoginTokenContextImpl.class);
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

	public Class<?> getTestRemoteLoginSystemLoginTokenContextClass() {
		return this.testRemoteLoginSystemLoginTokenContextClass;
	}

	public void setTestRemoteLoginSystemLoginTokenContextClass(Class<?> testRemoteLoginSystemLoginTokenContextClass) {
		if (testRemoteLoginSystemLoginTokenContextClass == null) {
			throw new IllegalArgumentException("testRemoteLoginSystemLoginTokenContextClass cannot be null.");
		}

		this.testRemoteLoginSystemLoginTokenContextClass = testRemoteLoginSystemLoginTokenContextClass;
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
	public void configureTokenSignatureFactories(AppConfiguration appConfig,
	                                             SpringBeansXMLBuilder builder) {

		String loginTokenSignatureFactoryId = this.getLoginTokenSignatureFactoryBeanId();
		String refreshTokenSignatureFactoryId = this.getRefreshTokenSignatureFactoryBeanId();

		String loginTokenSecret = EnvStringUtility.readProdEnv(PROD_LOGIN_TOKEN_SIGNATURE_SECRET_ENV_VAR, SIGNATURE_SECRET_NOT_SET_VALUE);
		String refreshTokenSecret = EnvStringUtility.readProdEnv(PROD_REFRESH_TOKEN_SIGNATURE_SECRET_ENV_VAR, SIGNATURE_SECRET_NOT_SET_VALUE);

		builder.bean(loginTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class).property("productionSecret").value(loginTokenSecret);
		builder.bean(refreshTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class).property("productionSecret").value(refreshTokenSecret);
	}

	@Override
	public void configureTokenEncoderDecoder(AppConfiguration appConfig,
	                                         SpringBeansXMLBeanBuilder<?> beanBuilder,
	                                         boolean forTests) {
		String loginTokenSignatureFactoryBeanId = this.getLoginTokenSignatureFactoryBeanId(appConfig, forTests);
		beanBuilder.beanClass(this.loginTokenEncoderDecoderClass).c().bean()
		        .factoryBean(loginTokenSignatureFactoryBeanId).factoryMethod("make");
	}

	protected String getLoginTokenSignatureFactoryBeanId(AppConfiguration appConfig,
	                                                     boolean forTests) {
		String beanId;

		if (this.shouldSetBeanForRemoteServerTests(forTests, appConfig)) {
			beanId = TEST_LOGIN_TOKEN_SIGNATURE_FACTORY_BEAN_ID;
		} else {
			beanId = this.loginTokenSignatureFactoryBeanId;
		}

		return beanId;
	}

	@Override
	public void configureTokenBuilder(AppConfiguration appConfig,
	                                  SpringBeansXMLBeanBuilder<?> beanBuilder,
	                                  boolean forTests) {
		String loginGetterBeanId = this.getTokenBuilderLoginGetterBeanId(appConfig, forTests);
		beanBuilder.beanClass(this.loginTokenBuilderClass).c().ref(loginGetterBeanId);
	}

	protected String getTokenBuilderLoginGetterBeanId(AppConfiguration appConfig,
	                                                  boolean forTests) {
		String loginGetterBeanId;

		if (this.shouldSetBeanForRemoteServerTests(forTests, appConfig)) {
			loginGetterBeanId = TEST_LOGIN_TOKEN_BUILDER_LOGIN_GETTER_BEAN_ID;
		} else {
			loginGetterBeanId = "loginGetter";	// Login Server already has this
			                                  	// available
		}

		return loginGetterBeanId;
	}

	/**
	 * Whether or not the bean being used/set will be a special case for the
	 * test server.
	 *
	 * @param forTests
	 * @param appConfig
	 * @return
	 */
	protected boolean shouldSetBeanForRemoteServerTests(boolean forTests,
	                                                    AppConfiguration appConfig) {
		return forTests && !appConfig.isLoginServer();
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

	@Override
	public void configureTestRemoteLoginSystemLoginTokenContext(AppConfiguration appConfig,
	                                                            SpringBeansXMLBeanBuilder<SpringBeansXMLBuilder> loginTokenContextBuilder) {
		loginTokenContextBuilder.beanClass(this.testRemoteLoginSystemLoginTokenContextClass).primary(false).c()
		        .ref("testLoginTokenService");
	}

}
