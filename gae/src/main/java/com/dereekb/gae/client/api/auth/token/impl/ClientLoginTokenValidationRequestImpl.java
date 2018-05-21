package com.dereekb.gae.client.api.auth.token.impl;

import com.dereekb.gae.client.api.auth.token.ClientLoginTokenValidationRequest;
import com.dereekb.gae.server.auth.security.token.model.SignedEncodedLoginToken;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link ClientLoginTokenValidationRequest} implementation.
 *
 * @author dereekb
 *
 */
public class ClientLoginTokenValidationRequestImpl
        implements ClientLoginTokenValidationRequest {

	private String token;
	private String content;
	private String signature;
	private Boolean includeClaims;

	public ClientLoginTokenValidationRequestImpl(String token) {
		this(token, true);
	}

	public ClientLoginTokenValidationRequestImpl(SignedEncodedLoginToken signedEncodedToken) {
		this(signedEncodedToken, true);
	}

	public ClientLoginTokenValidationRequestImpl(SignedEncodedLoginToken signedEncodedToken, Boolean includeClaims) {
		this(signedEncodedToken.getEncodedLoginToken(), signedEncodedToken.getTokenSignature(), includeClaims);
	}

	public ClientLoginTokenValidationRequestImpl(String token, Boolean includeClaims) {
		this(token, null, includeClaims);
	}

	public ClientLoginTokenValidationRequestImpl(String token, String signature) {
		this(token, signature, true);
	}

	public ClientLoginTokenValidationRequestImpl(String token, String signature, Boolean includeClaims) {
		this(token, null, signature, includeClaims);
	}

	public ClientLoginTokenValidationRequestImpl(String token,
	        String content,
	        String signature,
	        Boolean includeClaims) {
		super();
		this.setToken(token);
		this.setContent(content);
		this.setSignature(signature);
		this.setIncludeClaims(includeClaims);
	}

	@Override
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		if (StringUtility.isEmptyString(token)) {
			throw new IllegalArgumentException("token cannot be null or empty.");
		}

		this.token = token;
	}

	@Override
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String getSignature() {
		return this.signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	@Override
	public Boolean getIncludeClaims() {
		return this.includeClaims;
	}

	public void setIncludeClaims(Boolean includeClaims) {
		this.includeClaims = includeClaims;
	}

	@Override
	public String toString() {
		return "ClientLoginTokenValidationRequestImpl [token=" + this.token + ", content=" + this.content
		        + ", signature=" + this.signature + ", includeClaims=" + this.includeClaims + "]";
	}

}
