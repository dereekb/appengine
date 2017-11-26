package com.dereekb.gae.web.api.auth.response;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Used as a DTO to match a login pointer with the token specified.
 * <p>
 * The login pointer is optionally provided.
 *
 * @author dereekb
 *
 */
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginTokenPair {

	public static final String DATA_TYPE = "LOGIN_TOKEN_PAIR";

	private String loginPointer;
	private String token;

	public LoginTokenPair() {};

	public LoginTokenPair(String token) {
		this(null, token);
	}

	public LoginTokenPair(String loginPointer, String token) {
		this.setLoginPointer(loginPointer);
		this.setToken(token);
	}

	public static LoginTokenPair build(LoginPointer pointer,
	                                   String token) {
		String pointerId = null;

		if (pointer != null) {
			pointerId = pointer.getIdentifier();
		}

		return new LoginTokenPair(pointerId, token);
	}

	public String getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(String loginPointer) {
		this.loginPointer = loginPointer;
	}

	@JsonIgnore
	public ModelKey getLoginPointerKey() {
		return ModelKey.safe(this.loginPointer);
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "LoginTokenPair [loginPointer=" + this.loginPointer + ", token=" + this.token + "]";
	}

}
