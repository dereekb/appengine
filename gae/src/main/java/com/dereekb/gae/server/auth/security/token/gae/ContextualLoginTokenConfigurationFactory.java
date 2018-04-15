package com.dereekb.gae.server.auth.security.token.gae;

import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterDelegate;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenService;
import com.dereekb.gae.server.auth.security.token.refresh.RefreshTokenService;
import com.dereekb.gae.utilities.gae.GoogleAppEngineContextualFactory;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;

/**
 * {@link LoginToken} related components configuration factory. Builds
 * additional factories that build based on the app-engine environment.
 *
 * @author dereekb
 *
 */
@Deprecated
public class ContextualLoginTokenConfigurationFactory<T extends LoginToken> {

	private LoginTokenService<T> productionLoginTokenService;
	private RefreshTokenService productionRefreshTokenService;
	private LoginTokenAuthenticationFilterDelegate productionLoginTokenAuthenticationFilterDelegate;

	// MARK: Factory
	public LoginTokenService<T> makeLoginTokenService() {
		return this.makeLoginTokenServiceFactory().make();
	}

	public RefreshTokenService makeRefreshTokenService() {
		return this.makeRefreshTokenServiceFactory().make();
	}

	public LoginTokenAuthenticationFilterDelegate makeLoginTokenAuthenticationFilterDelegate() {
		return this.makeLoginTokenAuthenticationFilterDelegateFactory().make();
	}

	// MARK: Factories
	public GoogleAppEngineContextualFactory<LoginTokenService<T>> makeLoginTokenServiceFactory() {
		GoogleAppEngineContextualFactoryImpl<LoginTokenService<T>> factory = new GoogleAppEngineContextualFactoryImpl<LoginTokenService<T>>();

		factory.setProductionSingleton(this.productionLoginTokenService);

		return factory;
	}

	public GoogleAppEngineContextualFactory<RefreshTokenService> makeRefreshTokenServiceFactory() {
		GoogleAppEngineContextualFactoryImpl<RefreshTokenService> factory = new GoogleAppEngineContextualFactoryImpl<RefreshTokenService>();

		factory.setProductionSingleton(this.productionRefreshTokenService);

		return factory;
	}

	public GoogleAppEngineContextualFactory<LoginTokenAuthenticationFilterDelegate> makeLoginTokenAuthenticationFilterDelegateFactory() {
		GoogleAppEngineContextualFactoryImpl<LoginTokenAuthenticationFilterDelegate> factory = new GoogleAppEngineContextualFactoryImpl<LoginTokenAuthenticationFilterDelegate>();

		return factory;
	}

	private transient GoogleAppEngineContextualFactory<LoginTokenService<T>> loginTokenServiceFactory;
	private transient GoogleAppEngineContextualFactory<RefreshTokenService> refreshTokenServiceFactory;
	private transient GoogleAppEngineContextualFactory<LoginTokenAuthenticationFilterDelegate> authenticationFilterDelegateFactory;

}
