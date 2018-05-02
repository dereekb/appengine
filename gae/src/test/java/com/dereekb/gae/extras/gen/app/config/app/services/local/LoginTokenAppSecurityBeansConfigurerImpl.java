package com.dereekb.gae.extras.gen.app.config.app.services.local;

import com.dereekb.gae.extras.gen.app.config.app.AppConfiguration;
import com.dereekb.gae.extras.gen.app.config.app.services.AppSecurityBeansConfigurer;
import com.dereekb.gae.extras.gen.utility.spring.SpringBeansXMLBeanBuilder;
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

	private Class<?> loginTokenEncoderDecoderClass = LoginTokenEncoderDecoderImpl.class;
	private Class<?> loginTokenBuilderClass = LoginTokenBuilderImpl.class;
	private Class<?> loginTokenAuthenticationProviderClass = LoginTokenAuthenticationProviderImpl.class;
	private Class<?> loginTokenUserDetailsBuilderClass = LoginTokenUserDetailsBuilderImpl.class;

	private Class<?> loginSecurityModelQueryTaskOverrideClass = LoginSecurityModelQueryTaskOverrideImpl.class;

	public String getLoginTokenSignatureFactoryBeanId() {
		return this.loginTokenSignatureFactoryBeanId;
	}

	public void setLoginTokenSignatureFactoryBeanId(String loginTokenSignatureFactoryBeanId) {
		if (loginTokenSignatureFactoryBeanId == null) {
			throw new IllegalArgumentException("loginTokenSignatureFactoryBeanId cannot be null.");
		}

		this.loginTokenSignatureFactoryBeanId = loginTokenSignatureFactoryBeanId;
	}

	public String getRefreshTokenSignatureFactoryBeanId() {
		return this.refreshTokenSignatureFactoryBeanId;
	}

	public void setRefreshTokenSignatureFactoryBeanId(String refreshTokenSignatureFactoryBeanId) {
		if (refreshTokenSignatureFactoryBeanId == null) {
			throw new IllegalArgumentException("refreshTokenSignatureFactoryBeanId cannot be null.");
		}

		this.refreshTokenSignatureFactoryBeanId = refreshTokenSignatureFactoryBeanId;
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
		beanBuilder.beanClass(this.loginTokenBuilderClass).c().ref("loginRegistry");
	}

	@Override
	public void configureTokenAuthenticationProvider(AppConfiguration appConfig,
	                                                 SpringBeansXMLBeanBuilder<?> beanBuilder) {
		beanBuilder.beanClass(this.loginTokenAuthenticationProviderClass);
	}

	@Override
	public void configureTokenUserDetailsBuilder(AppConfiguration appConfig,
	                                             SpringBeansXMLBeanBuilder<?> beanBuilder) {
		beanBuilder.beanClass(LoginTokenUserDetailsBuilderImpl.class).c().ref("loginTokenModelContextSetDencoder")
		        .ref("loginTokenGrantedAuthorityBuilder").ref("loginRegistry").ref("loginPointerRegistry");
	}

}
