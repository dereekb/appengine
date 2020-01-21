package com.dereekb.gae.server.auth.security.token.gae;

import com.dereekb.gae.server.auth.security.token.model.SignatureConfiguration;
import com.dereekb.gae.server.auth.security.token.model.impl.SignatureConfigurationImpl;
import com.dereekb.gae.utilities.gae.GoogleAppEngineContextualFactory;
import com.dereekb.gae.utilities.gae.impl.GoogleAppEngineContextualFactoryImpl;
import com.dereekb.gae.utilities.model.lazy.exception.UnavailableSourceObjectException;
import com.dereekb.gae.utilities.model.source.Source;

/**
 * {@link GoogleAppEngineContextualFactory} for {@link SignatureConfiguration}.
 *
 * @author dereekb
 *
 */
public class SignatureConfigurationFactory extends GoogleAppEngineContextualFactoryImpl<SignatureConfiguration> {

	public static final String TEST_REFRESH_SIGNATURE_SECRET = "MzXHEF63iSLa6OksSfZ4hvtXZMTHu7QNkhUToOaAQPel29NwutgFGiV68XYrcJF";
	public static final String DEV_REFRESH_SIGNATURE_SECRET = "C7D0hkvFGJESTg1CpFZpGxGtEqLOs7AU06DwJjI9zNm6lBTP1OQx9Dv3tQFjb88";

	public static final String TEST_LOGIN_SIGNATURE_SECRET = "MzXHEF63iSLa6OksSfZ4hvtXZMTHb7QNkhUToOaAQPel29NwutgFGiV68XYrcJF";
	public static final String DEV_LOGIN_SIGNATURE_SECRET = "C7D0hkvFGJESTg1CpFZpGxGtEqLOs8AU06DwJjI9zNm6lBTP1OQx9Dv3tQFjb88";

	public SignatureConfigurationFactory() {
		this(true);
	}

	public SignatureConfigurationFactory(boolean refreshTokenService) {
		super(true);

		this.setTestSource(new Source<SignatureConfiguration>() {

			@Override
			public SignatureConfiguration loadObject() throws RuntimeException, UnavailableSourceObjectException {
				String secret = (refreshTokenService) ? TEST_REFRESH_SIGNATURE_SECRET : TEST_LOGIN_SIGNATURE_SECRET;
				return new SignatureConfigurationImpl(secret);
			}

		});

		this.setDevelopmentSource(new Source<SignatureConfiguration>() {

			@Override
			public SignatureConfiguration loadObject() throws RuntimeException, UnavailableSourceObjectException {
				String secret = (refreshTokenService) ? DEV_REFRESH_SIGNATURE_SECRET : DEV_LOGIN_SIGNATURE_SECRET;
				return new SignatureConfigurationImpl(secret);
			}

		});
	}

	public void setProductionSecret(String secret) {
		this.setProductionSingleton(new SignatureConfigurationImpl(secret));
	}

}
