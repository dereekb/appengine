package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.exception.UnavailableAppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityDetailsService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.app.service.LoginTokenVerifierRequest;
import com.dereekb.gae.server.auth.security.token.exception.TokenException;
import com.dereekb.gae.server.auth.security.token.exception.TokenSignatureInvalidException;
import com.dereekb.gae.server.auth.security.token.exception.TokenUnauthorizedException;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.data.ValueUtility;

/**
 * {@link AppLoginSecurityVerifierService} that uses {@link App} to verify
 * tokens and requests.
 * <p>
 * Verifies that tokens are properly signed for cases where an app is declared.
 *
 * @author dereekb
 *
 */
public class AppLoginSecurityVerifierServiceImpl extends AbstractAppLoginSecurityVerifierServiceImpl
        implements AppLoginSecurityVerifierService {

	private AppLoginSecurityDetailsService detailsService;
	private AppLoginSecuritySigningService signingService;

	public AppLoginSecurityVerifierServiceImpl(AppLoginSecurityDetailsService detailsService,
	        AppLoginSecuritySigningService signingService) {
		super();
		this.setDetailsService(detailsService);
		this.setSigningService(signingService);
	}

	public AppLoginSecurityDetailsService getDetailsService() {
		return this.detailsService;
	}

	public void setDetailsService(AppLoginSecurityDetailsService detailsService) {
		if (detailsService == null) {
			throw new IllegalArgumentException("detailsService cannot be null.");
		}

		this.detailsService = detailsService;
	}

	public AppLoginSecuritySigningService getSigningService() {
		return this.signingService;
	}

	public void setSigningService(AppLoginSecuritySigningService signingService) {
		if (signingService == null) {
			throw new IllegalArgumentException("signingService cannot be null.");
		}

		this.signingService = signingService;
	}

	// MARK: AppLoginSecurityVerifierService
	@Override
	public void assertValidTokenSignature(LoginTokenVerifierRequest request) throws TokenException {

		DecodedLoginToken<?> token = request.getLoginToken();
		LoginToken loginToken = token.getLoginToken();
		String app = loginToken.getApp();

		try {

			// Load the App
			if (app != null) {
				ModelKey key = ModelKey.convertNumberString(app);
				AppLoginSecurityDetails appDetails = this.detailsService.getAppLoginDetails(key);

				// Load Signature
				String signature = request.getSignature();

				if (StringUtility.isEmptyString(signature)) {
					throw new TokenSignatureInvalidException("Required signature is missing.");
				}

				// Compare the Keys
				String secret = appDetails.getAppSecret();
				String encodedToken = token.getEncodedLoginToken();
				String content = ValueUtility.defaultTo(request.getContent(), "");

				String signed = this.signingService.hexSign(secret, encodedToken, content);

				if (signed.equals(signature) == false) {
					throw new TokenSignatureInvalidException("Signatures do not match.");
				}
			} else if (loginToken.getPointerType() == LoginPointerType.SYSTEM) {
				throw new TokenSignatureInvalidException("Use of system tokens requires a signature.");
			}
		} catch (UnavailableAppLoginSecurityDetails e) {
			// False if key is not a number.
			throw new TokenUnauthorizedException("Unknown app specified in token.");
		} catch (NumberFormatException e) {
			// False if key is not a number.
			throw new TokenUnauthorizedException("Invalid app specified. Improperly forged token?");
		}
	}

	@Override
	public String toString() {
		return "AppLoginSecurityVerifierServiceImpl [detailsService=" + this.detailsService + ", signingService="
		        + this.signingService + "]";
	}

}
