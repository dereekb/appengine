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

	public static final String TEST_SIGNATURE_SECRET = "MzXHEF63iSLa6OksSfZ4hvtXZMTHu7QNkhUToOaAQPel29NwutgFGiV68XYrcJF";
	public static final String DEV_SIGNATURE_SECRET = "C7D0hkvFGJESTg1CpFZpGxGtEqLOs7AU06DwJjI9zNm6lBTP1OQx9Dv3tQFjb88";

	public SignatureConfigurationFactory() {
		super(true);

		this.setTestSource(new Source<SignatureConfiguration>() {

			@Override
			public SignatureConfiguration loadObject() throws RuntimeException, UnavailableSourceObjectException {
				return new SignatureConfigurationImpl(TEST_SIGNATURE_SECRET);
			}

		});

		this.setDevelopmentSource(new Source<SignatureConfiguration>() {

			@Override
			public SignatureConfiguration loadObject() throws RuntimeException, UnavailableSourceObjectException {
				return new SignatureConfigurationImpl(DEV_SIGNATURE_SECRET);
			}

		});
	}

	public void setProductionSecret(String secret) {
		this.setProductionSingleton(new SignatureConfigurationImpl(secret));
	}

}
