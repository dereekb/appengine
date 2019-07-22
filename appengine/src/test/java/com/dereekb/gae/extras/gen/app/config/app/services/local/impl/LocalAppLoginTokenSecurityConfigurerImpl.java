package com.dereekb.gae.extras.gen.app.config.app.services.local.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppLoginTokenSecurityConfigurer;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.server.auth.security.login.impl.LoginPointerServiceImpl;
import com.dereekb.gae.server.auth.security.login.impl.LoginRegisterServiceImpl;
import com.dereekb.gae.server.auth.security.login.impl.LoginRolesServiceImpl;
import com.dereekb.gae.server.auth.security.login.key.impl.KeyLoginAuthenticationServiceImpl;
import com.dereekb.gae.server.auth.security.login.key.impl.KeyLoginStatusServiceManagerImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.manager.OAuthServiceManagerImpl;
import com.dereekb.gae.server.auth.security.login.oauth.impl.service.OAuthLoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.impl.PasswordLoginServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.PasswordRecoveryServiceEmailDelegateImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.PasswordRecoveryServiceImpl;
import com.dereekb.gae.server.auth.security.login.password.recover.impl.PasswordRecoveryServiceTokenDelegateImpl;
import com.dereekb.gae.server.auth.security.token.gae.SignatureConfigurationFactory;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenServiceImpl;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.refresh.impl.RefreshTokenServiceImpl;

/**
 * {@link AppLoginTokenSecurityConfigurer} implementation for a local login
 * service.
 *
 * @author dereekb
 *
 */
public class LocalAppLoginTokenSecurityConfigurerImpl
        implements AppLoginTokenSecurityConfigurer {

	// MARK: AppLoginTokenSecurityConfigurer
	@Override
	public void configureLoginTokenSecurityServiceComponents(AppConfiguration appConfig,
	                                                         SpringBeansXMLBuilder builder) {

		AppSecurityBeansConfigurer appSecurityBeansConfigurer = appConfig.getAppSecurityBeansConfigurer();

		// TODO: Update to pull signatures from JSON file or similar file
		// not included in git, or from ENV

		builder.comment("Local Login Service");
		builder.comment("Signatures");
		String loginTokenSignatureFactoryId = appSecurityBeansConfigurer.getLoginTokenSignatureFactoryBeanId();
		String refreshTokenSignatureFactoryId = appSecurityBeansConfigurer.getRefreshTokenSignatureFactoryBeanId();

		builder.bean(loginTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class).getRawXMLBuilder()
		        .comment("TODO: Add production source.");
		builder.bean(refreshTokenSignatureFactoryId).beanClass(SignatureConfigurationFactory.class).getRawXMLBuilder()
		        .comment("TODO: Add production source.");

		builder.comment("LoginToken Service");
		String loginTokenEncoderDecoderBeanId = "loginTokenEncoderDecoder";

		SpringBeansXMLBeanBuilder<?> loginTokenEncoderDecoderBuilder = builder.bean(loginTokenEncoderDecoderBeanId);
		appSecurityBeansConfigurer.configureTokenEncoderDecoder(appConfig, loginTokenEncoderDecoderBuilder);

		String loginTokenBuilderBeanId = "loginTokenBuilder";

		SpringBeansXMLBeanBuilder<?> loginTokenBuilderBuilder = builder.bean(loginTokenBuilderBeanId);
		appSecurityBeansConfigurer.configureTokenBuilder(appConfig, loginTokenBuilderBuilder);

		builder.bean(appConfig.getAppBeans().getLoginTokenServiceBeanId()).beanClass(LoginTokenServiceImpl.class).c()
		        .ref(loginTokenBuilderBeanId).ref(loginTokenEncoderDecoderBeanId);

		// Alias the decoder
		builder.alias(appConfig.getAppBeans().getLoginTokenServiceBeanId(),
		        appConfig.getAppBeans().getLoginTokenDecoderBeanId());

		// MARK: Local Login Service
		builder.comment("LoginPointer Service");
		builder.bean("loginPointerService").beanClass(LoginPointerServiceImpl.class).c().ref("loginPointerRegistry")
		        .ref("loginPointerScheduleCreateReview");

		builder.comment("Password Service");
		builder.bean("passwordEncoder").beanClass(BCryptPasswordEncoder.class);

		builder.bean(appConfig.getAppBeans().getUtilityBeans().getPasswordLoginServiceBeanId())
		        .beanClass(PasswordLoginServiceImpl.class).c().ref("passwordEncoder").ref("loginPointerService");

		builder.bean("passwordRecoveryService").beanClass(PasswordRecoveryServiceImpl.class).c().ref("mailService")
		        .bean().beanClass(PasswordRecoveryServiceTokenDelegateImpl.class).c().ref("loginPointerService")
		        .ref(appConfig.getAppBeans().getLoginTokenServiceBeanId()).up().up().bean()
		        .beanClass(PasswordRecoveryServiceEmailDelegateImpl.class);

		builder.comment("OAuth Service");
		builder.bean("oAuthLoginService").beanClass(OAuthLoginServiceImpl.class).c().ref("loginPointerService");

		String oAuthLoginServiceMapId = "oAuthLoginServiceMap";
		appSecurityBeansConfigurer.configureOAuthServiceManagerMap(appConfig, builder, oAuthLoginServiceMapId);

		builder.bean("oAuthServiceManager").beanClass(OAuthServiceManagerImpl.class).c().ref("oAuthLoginService")
		        .ref(oAuthLoginServiceMapId);

		builder.comment("KeyLogin Service");
		builder.bean("keyLoginStatusServiceManager").beanClass(KeyLoginStatusServiceManagerImpl.class).c()
		        .ref("loginPointerRegistry");

		builder.bean("keyLoginAuthenticationService").beanClass(KeyLoginAuthenticationServiceImpl.class).c()
		        .ref("loginKeyRegistry").ref("loginPointerRegistry");

		builder.comment("Register Service");
		builder.bean(appConfig.getAppBeans().getUtilityBeans().getLoginRegisterServiceBeanId())
		        .beanClass(LoginRegisterServiceImpl.class).c().ref("newLoginGenerator").ref("loginRegistry")
		        .ref("loginPointerRegistry");

		String newLoginGeneratorId = "newLoginGenerator";
		appSecurityBeansConfigurer.configureNewLoginGenerator(appConfig, builder, newLoginGeneratorId);

		builder.bean(appConfig.getAppBeans().getUtilityBeans().getLoginRolesServiceBeanId())
		        .beanClass(LoginRolesServiceImpl.class).c()
		        .ref("loginRegistry")
		        .ref(appConfig.getAppBeans().getUtilityBeans().getLoginAdminRolesBeanId());

		builder.comment("Refresh Token Service");
		builder.bean("refreshTokenService").beanClass(RefreshTokenServiceImpl.class).c().ref("loginRegistry")
		        .ref("loginPointerRegistry");

		builder.bean("refreshTokenEncoderDecoder").beanClass(RefreshTokenEncoderDecoder.class).c().bean()
		        .factoryBean("refreshTokenSignatureFactory").factoryMethod("make");

	}

}
