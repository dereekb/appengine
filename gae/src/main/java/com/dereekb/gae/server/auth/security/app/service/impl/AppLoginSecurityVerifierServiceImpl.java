package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.AppLoginSecurityDetails;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityDetailsService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.AppLoginSecurityVerifierService;
import com.dereekb.gae.server.auth.security.token.model.DecodedLoginToken;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AppLoginSecurityVerifierService} that uses {@link App} to verify
 * requests.
 *
 * @author dereekb
 *
 */
public class AppLoginSecurityVerifierServiceImpl
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

	// MARK:
	@Override
	public boolean isValidToken(DecodedLoginToken<?> token,
	                            String signature) {

		LoginToken loginToken = token.getLoginToken();
		String app = loginToken.getApp();

		try {

			// Load the App
			if (app != null) {
				ModelKey key = ModelKey.convertNumberString(app);
				AppLoginSecurityDetails appDetails = this.detailsService.getAppLoginDetails(key);

				// Compare the Keys
				String secret = appDetails.getAppSecret();
				String encodedToken = token.getEncodedLoginToken();

				String signed = this.signingService.hexSign(secret, encodedToken);
				return signed.equals(signature);
			}
		} catch (NumberFormatException e) {
			// False if key is not a number.
		}

		return false;
	}

	@Override
	public String toString() {
		return "AppLoginSecurityVerifierServiceImpl [detailsService=" + this.detailsService + ", signingService="
		        + this.signingService + "]";
	}

}
