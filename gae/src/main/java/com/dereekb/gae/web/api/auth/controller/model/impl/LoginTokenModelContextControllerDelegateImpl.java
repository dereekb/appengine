package com.dereekb.gae.web.api.auth.controller.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.LoginTokenModelContextSetEncoder;
import com.dereekb.gae.server.auth.security.model.context.service.LoginTokenModelContextService;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginTokenEncoderDecoder;
import com.dereekb.gae.server.auth.security.token.model.impl.LoginTokenImpl;
import com.dereekb.gae.web.api.auth.controller.model.LoginTokenModelContextControllerDelegate;

/**
 * {@link LoginTokenModelContextControllerDelegate} implementation for
 * {@link LoginToken}.
 *
 * @author dereekb
 *
 */
public class LoginTokenModelContextControllerDelegateImpl extends AbstractLoginTokenModelContextControllerDelegateImpl<LoginToken> {

	public LoginTokenModelContextControllerDelegateImpl(LoginTokenModelContextService service,
	        LoginTokenEncoderDecoder<LoginToken> loginTokenEncoderDecoder,
	        LoginTokenModelContextSetEncoder modelContextSetEncoder) {
		super(service, loginTokenEncoderDecoder, modelContextSetEncoder);
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
