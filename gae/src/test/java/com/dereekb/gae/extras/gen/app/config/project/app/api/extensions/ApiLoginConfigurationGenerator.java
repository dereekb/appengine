package com.dereekb.gae.extras.gen.app.config.project.app.api.extensions;

import java.util.Properties;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.impl.AbstractSingleConfigurationFileGenerator;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBuilder;
import com.dereekb.gae.extras.gen.utility.spring.impl.SpringBeansXMLBuilderImpl;
import com.dereekb.gae.web.api.auth.controller.key.KeyLoginController;
import com.dereekb.gae.web.api.auth.controller.key.impl.KeyLoginControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.controller.oauth.OAuthLoginController;
import com.dereekb.gae.web.api.auth.controller.oauth.impl.OAuthLoginControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.controller.password.PasswordLoginController;
import com.dereekb.gae.web.api.auth.controller.password.impl.PasswordLoginControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.controller.register.LoginRegisterController;
import com.dereekb.gae.web.api.auth.controller.register.impl.LoginRegisterControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.controller.system.SystemLoginTokenController;
import com.dereekb.gae.web.api.auth.controller.system.impl.SystemLoginTokenControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.controller.token.LoginTokenController;
import com.dereekb.gae.web.api.auth.controller.token.impl.LoginTokenControllerDelegateImpl;
import com.dereekb.gae.web.api.auth.exception.handler.ApiLoginExceptionHandler;

public class ApiLoginConfigurationGenerator extends AbstractSingleConfigurationFileGenerator {

	public static final String LOGIN_FILE_NAME = "login";

	public ApiLoginConfigurationGenerator(AppConfiguration appConfig, Properties outputProperties) {
		super(appConfig, outputProperties);
		this.setFileName(LOGIN_FILE_NAME);
	}

	// MARK: AbstractConfigurationFileGenerator
	@Override
	public SpringBeansXMLBuilder makeXMLConfigurationFile() throws UnsupportedOperationException {
		SpringBeansXMLBuilder builder = SpringBeansXMLBuilderImpl.make();

		builder.comment("Register Controller");
		String loginRegisterControllerDelegateBeanId = "loginRegisterControllerDelegate";

		builder.bean("loginRegisterController").beanClass(LoginRegisterController.class).c()
		        .ref(loginRegisterControllerDelegateBeanId);

		builder.bean(loginRegisterControllerDelegateBeanId).beanClass(LoginRegisterControllerDelegateImpl.class).c()
		        .ref("loginRegisterService").ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId());

		builder.comment("Token Controller");
		String loginTokenControllerDelegateBeanId = "loginTokenControllerDelegate";

		builder.bean("loginTokenController").beanClass(LoginTokenController.class).c()
		        .ref(loginTokenControllerDelegateBeanId);

		builder.bean(loginTokenControllerDelegateBeanId).beanClass(LoginTokenControllerDelegateImpl.class).c()
		        .ref("refreshTokenEncoderDecoder").ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId())
		        .ref("refreshTokenService").ref("appLoginSecurityService");

		builder.comment("System Token Controller");
		String systemLoginTokenControllerDelegateBeanId = "systemLoginTokenControllerDelegate";

		builder.bean("systemLoginTokenController").beanClass(SystemLoginTokenController.class).c()
		        .ref(systemLoginTokenControllerDelegateBeanId);

		builder.bean(systemLoginTokenControllerDelegateBeanId).beanClass(SystemLoginTokenControllerDelegateImpl.class)
		        .c().ref(this.getAppConfig().getAppBeans().getSystemLoginTokenServiceBeanId());

		builder.comment("Anonymous Login Controller");

		builder.comment("Password Login Controller");
		String passwordLoginControllerDelegateBeanId = "passwordLoginControllerDelegate";

		builder.bean("passwordLoginController").beanClass(PasswordLoginController.class).c()
		        .ref(passwordLoginControllerDelegateBeanId);

		builder.bean(passwordLoginControllerDelegateBeanId).beanClass(PasswordLoginControllerDelegateImpl.class).c()
		        .ref("passwordLoginService").ref("passwordRecoveryService")
		        .ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId());

		builder.comment("OAuth");
		String oAuthLoginControllerDelegateBeanId = "oAuthLoginControllerDelegate";

		builder.bean("oAuthLoginController").beanClass(OAuthLoginController.class).c()
		        .ref(oAuthLoginControllerDelegateBeanId);
		builder.bean(oAuthLoginControllerDelegateBeanId).beanClass(OAuthLoginControllerDelegateImpl.class).c()
		        .ref("oAuthServiceManager").ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId());

		builder.comment("API Key Login Controller");
		String keyLoginControllerDelegateBeanId = "keyLoginControllerDelegate";

		builder.bean("keyLoginController").beanClass(KeyLoginController.class).c()
		        .ref(keyLoginControllerDelegateBeanId);

		builder.bean(keyLoginControllerDelegateBeanId).beanClass(KeyLoginControllerDelegateImpl.class).c()
		        .ref(this.getAppConfig().getAppBeans().getLoginTokenServiceBeanId()).ref("keyLoginStatusServiceManager")
		        .ref("keyLoginAuthenticationService");

		builder.comment("Exception Handler");
		builder.bean("loginExceptionHandler").beanClass(ApiLoginExceptionHandler.class);

		return builder;
	}

}
