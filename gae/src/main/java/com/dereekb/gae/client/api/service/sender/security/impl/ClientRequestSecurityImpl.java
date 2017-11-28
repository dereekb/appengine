package com.dereekb.gae.client.api.service.sender.security.impl;

import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurity;
import com.dereekb.gae.client.api.service.sender.security.ClientRequestSecurityContextType;
import com.dereekb.gae.server.auth.security.token.model.EncodedLoginToken;

/**
 * {@link ClientRequestSecurity} implementation.
 *
 * @author dereekb
 *
 */
public class ClientRequestSecurityImpl
        implements ClientRequestSecurity {

	private EncodedLoginToken overrideToken;
	private ClientRequestSecurityContextType securityContextType;

	public ClientRequestSecurityImpl() {
		this(ClientRequestSecurityContextType.CURRENT);
	};

	public ClientRequestSecurityImpl(EncodedLoginToken overrideToken) {
		this();
		this.setOverrideToken(overrideToken);
		this.setSecurityContextType(ClientRequestSecurityContextType.OVERRIDE);
	}

	private ClientRequestSecurityImpl(ClientRequestSecurityContextType securityContextType) {
		this.setSecurityContextType(securityContextType);
	}

	public static ClientRequestSecurityImpl systemSecurity() {
		return new ClientRequestSecurityImpl(ClientRequestSecurityContextType.SYSTEM);
	}

	public static ClientRequestSecurity current() {
		return new ClientRequestSecurityImpl(ClientRequestSecurityContextType.CURRENT);
	}

	// MARK: ClientRequestSecurity
	@Override
	public EncodedLoginToken getOverrideToken() {
		return this.overrideToken;
	}

	public void setOverrideToken(EncodedLoginToken overrideToken) {
		this.overrideToken = overrideToken;
	}

	@Override
	public ClientRequestSecurityContextType getSecurityContextType() {
		return this.securityContextType;
	}

	public void setSecurityContextType(ClientRequestSecurityContextType securityContextType) {
		if (securityContextType == null) {
			throw new IllegalArgumentException("securityContextType cannot be null.");
		}

		this.securityContextType = securityContextType;
	}

	@Override
	public String toString() {
		return "ClientRequestSecurityImpl [overrideToken=" + this.overrideToken + ", securityContextType="
		        + this.securityContextType + "]";
	}

}
