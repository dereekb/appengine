package com.dereekb.gae.server.auth.security.app.service.impl;

import com.dereekb.gae.server.auth.security.app.service.AppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.app.service.ConfiguredAppLoginSecuritySigningService;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;

/**
 * Abstract {@link ConfiguredAppLoginSecuritySigningService} implementation that wraps a
 * {@link AppLoginSecuritySigningService}.
 *
 * @author dereekb
 *
 */
public abstract class AbstractConfiguredAppLoginSecuritySigningServiceImpl
        implements ConfiguredAppLoginSecuritySigningService {

	private AppLoginSecuritySigningService service;

	public AbstractConfiguredAppLoginSecuritySigningServiceImpl(AppLoginSecuritySigningService service) {
		super();
		this.setService(service);
	}

	public AppLoginSecuritySigningService getService() {
		return this.service;
	}

	public void setService(AppLoginSecuritySigningService service) {
		if (service == null) {
			throw new IllegalArgumentException("service cannot be null.");
		}

		this.service = service;
	}

	protected String getSecret() {
		return this.getAppDetails().getAppSecret();
	}

	// MARK: ConfiguredAppLoginSecuritySigningService
	@Override
	public SignedEncodedLoginToken signToken(String token) {
		return this.signToken(this.getSecret(), token);
	}

	@Override
	public String hexSign(String token) throws IllegalArgumentException {
		return this.hexSign(this.getSecret(), token);
	}

	@Override
	public byte[] byteSign(String token) throws IllegalArgumentException {
		return this.byteSign(this.getSecret(), token);
	}

	// MARK: AppLoginSecuritySigningService
	@Override
	public SignedEncodedLoginToken signToken(String secret,
	                                         String token)
	        throws IllegalArgumentException {
		return this.service.signToken(secret, token);
	}

	@Override
	public String hexSign(String secret,
	                      String token)
	        throws IllegalArgumentException {
		return this.service.hexSign(secret, token);
	}

	@Override
	public byte[] byteSign(String secret,
	                       String token)
	        throws IllegalArgumentException {
		return this.service.byteSign(secret, token);
	}

	@Override
	public String toString() {
		return "AbstractConfiguredAppLoginSecuritySigningServiceImpl [service=" + this.service + "]";
	}

}
