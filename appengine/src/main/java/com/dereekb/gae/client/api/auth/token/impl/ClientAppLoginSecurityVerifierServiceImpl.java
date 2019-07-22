package com.dereekb.gae.client.api.auth.token.impl;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationService;
import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationServiceRequestSender;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenExpiredException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidException;
import com.dereekb.gae.client.api.auth.token.exception.ClientLoginTokenInvalidSignatureException;
import com.dereekb.gae.client.api.exception.ClientIllegalArgumentException;
import com.dereekb.gae.client.api.exception.ClientRequestFailureException;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.app.service.impl.AbstractAppLoginSecurityVerifierServiceImpl;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenExpiredException;
import com.dereekb.gae.server.auth.security.token.exception.TokenSignatureInvalidException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;

/**
 * {@link AppLoginSecurityVerifierService} that uses a
 * {@link ClientLoginTokenValidationServiceRequestSender}.
 *
 * @author dereekb
 *
 */
public class ClientAppLoginSecurityVerifierServiceImpl extends AbstractAppLoginSecurityVerifierServiceImpl
        implements AppLoginSecurityVerifierService {

	private static final Logger LOGGER = Logger.getLogger(ClientAppLoginSecurityVerifierServiceImpl.class.getName());

	private ClientLoginTokenValidationService clientLoginTokenValidationService;

	public ClientAppLoginSecurityVerifierServiceImpl(
	        ClientLoginTokenValidationService clientLoginTokenValidationService) {
		super();
		this.setClientLoginTokenValidationService(clientLoginTokenValidationService);
	}

	public ClientLoginTokenValidationService getClientLoginTokenValidationService() {
		return this.clientLoginTokenValidationService;
	}

	public void setClientLoginTokenValidationService(ClientLoginTokenValidationService clientLoginTokenValidationService) {
		if (clientLoginTokenValidationService == null) {
			throw new IllegalArgumentException("clientLoginTokenValidationService cannot be null.");
		}

		this.clientLoginTokenValidationService = clientLoginTokenValidationService;
	}

	// MARK: AppLoginSecurityVerifierService
	@Override
	public void assertValidTokenSignature(LoginTokenVerifierRequest request) throws TokenException {
		try {
			this.clientLoginTokenValidationService.validateToken(new ClientLoginTokenValidationRequestImpl(request));
		} catch (ClientLoginTokenExpiredException e) {
			throw new TokenExpiredException(e);
		} catch (ClientLoginTokenInvalidException e) {
			throw new TokenUnauthorizedException(e);
		} catch (ClientLoginTokenInvalidSignatureException e) {
			throw new TokenSignatureInvalidException(e);
		} catch (ClientIllegalArgumentException e) {
			LOGGER.log(Level.WARNING, "Illegal argument while checking token.", e);
			throw new TokenUnauthorizedException();
		} catch (ClientRequestFailureException e) {
			LOGGER.log(Level.WARNING, "The client request failed while attempting to authorize the token.", e);
			throw new TokenUnauthorizedException(e);
		}
	}

	@Override
	public String toString() {
		return "ClientAppLoginSecurityVerifierServiceImpl [clientLoginTokenValidationService="
		        + this.clientLoginTokenValidationService + "]";
	}

}
