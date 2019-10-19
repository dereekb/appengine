package com.dereekb.gae.web.api.auth.controller.model.context.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoder;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.web.api.auth.controller.model.context.LoginTokenModelContextControllerDelegate;

/**
 * {@link LoginTokenModelContextControllerDelegate} implementation for
 * {@link LoginToken}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class LoginTokenModelContextControllerDelegateImpl extends AbstractLoginTokenModelContextControllerDelegateImpl<LoginToken> {

	public LoginTokenModelContextControllerDelegateImpl(LoginTokenModelContextService service,
	        LoginTokenModelContextSetEncoder modelContextSetEncoder,
	        LoginTokenEncoderDecoder<LoginToken> loginTokenEncoderDecoder) {
		super(service, modelContextSetEncoder, loginTokenEncoderDecoder);
	}

	@Override
	protected LoginToken getCurrentToken() {
		return LoginSecurityContext.getAuthentication().getCredentials().getLoginToken();
	}

	@Override
	protected LoginToken makeNewToken(LoginToken token,
	                                  Date expires,
	                                  EncodedLoginTokenModelContextSet encodedSet) {
		LoginTokenImpl newToken = (LoginTokenImpl) this.getLoginTokenEncoderDecoder().makeToken(token);
		newToken.setExpiration(expires);
		newToken.setEncodedModelContextSet(encodedSet);
		return newToken;
	}

}
