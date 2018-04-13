package com.dereekb.gae.server.auth.security.app.token.filter;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.app.service.impl.LoginTokenVerifierRequestImpl;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenSignatureInvalidException;
import com.dereekb.gae.server.auth.security.token.filter.LoginTokenAuthenticationFilterVerifier;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.parameter.AuthenticationParameterReader;
import com.dereekb.gae.server.auth.security.token.parameter.impl.AuthenticationParameterServiceImpl;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * Used for verifying an app request.
 * <p>
 * Forces all system tokens to have an app set and with valid proof.
 *
 * @author dereekb
 *
 */
public class LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl
        implements LoginTokenAuthenticationFilterVerifier<LoginToken> {

	private static final Logger LOGGER = Logger
	        .getLogger(LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl.class.getName());

	private AppLoginSecurityVerifierService service;
	private AuthenticationParameterReader reader;

	public LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl(AppLoginSecurityVerifierService service) {
		this(service, AuthenticationParameterServiceImpl.SINGLETON);
	}

	public LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl(AppLoginSecurityVerifierService service,
	        AuthenticationParameterReader reader) {
		this.setService(service);
		this.setReader(reader);
	}

	public AppLoginSecurityVerifierService getService() {
		return this.service;
	}

	public void setService(AppLoginSecurityVerifierService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
	}

	public AuthenticationParameterReader getReader() {
		return this.reader;
	}

	public void setReader(AuthenticationParameterReader reader) {
		if (reader == null) {
			throw new IllegalArgumentException("reader cannot be null.");
		}

		this.reader = reader;
	}

	// MARK: LoginTokenAuthenticationFilterVerifier
	@Override
	public void assertValidDecodedLoginToken(DecodedLoginToken<LoginToken> decodedLoginToken,
	                                         HttpServletRequest request)
	        throws TokenException {

		LoginToken token = decodedLoginToken.getLoginToken();
		String app = token.getApp();

		// No need to verify the signature.
		if (StringUtility.isEmptyString(app) == false) {
			String signatureHeader = this.reader.readSignature(request);

			// TODO: Content for token signatures is always empty, but we may
			// add full-request signing and verification here. Content is pulled
			// from request.
			String content = "";

			LoginTokenVerifierRequest verifierRequest = new LoginTokenVerifierRequestImpl(decodedLoginToken, content,
			        signatureHeader);

			this.service.assertValidTokenSignature(verifierRequest);

			// TODO: If not valid, but the request is the the taskqueue,
			// then try again with any older/stored secrets. ONLY valid for
			// the taskqueue... This is for legacy systems.

		} else if (token.getPointerType() == LoginPointerType.SYSTEM) {
			String address = request.getRemoteAddr();
			LOGGER.log(Level.SEVERE,
			        "System Login Token rejected for no signature from: " + address + " token: " + token.toString());
			throw new TokenSignatureInvalidException("Use of system tokens requires a signature.");
		}
	}

	@Override
	public String toString() {
		return "LoginTokenAuthenticationFilterAppLoginSecurityVerifierImpl [service=" + this.service + ", reader="
		        + this.reader + "]";
	}

}
