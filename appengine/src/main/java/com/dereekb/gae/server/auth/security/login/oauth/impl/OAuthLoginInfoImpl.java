package com.dereekb.gae.server.auth.security.login.oauth.impl;

import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.oauth.OAuthLoginInfo;

/**
 * {@link OAuthLoginInfo} implementation.
 *
 * @author dereekb
 *
 */
public class OAuthLoginInfoImpl
        implements OAuthLoginInfo {

	private LoginPointerType loginType;
	private String id;
	private String name;
	private String email;
	private boolean acceptable = true;

	public OAuthLoginInfoImpl() {}

	public OAuthLoginInfoImpl(LoginPointerType loginType, String id) {
		this(loginType, id, null, null);
	}

	public OAuthLoginInfoImpl(LoginPointerType loginType, String id, String name, String email) {
		this(loginType, id, name, email, true);
	}

	public OAuthLoginInfoImpl(LoginPointerType loginType, String id, String name, String email, boolean acceptable) {
		this.loginType = loginType;
		this.id = id;
		this.name = name;
		this.email = email;
		this.acceptable = acceptable;
	}

	@Override
	public LoginPointerType getLoginType() {
		return this.loginType;
	}

	public void setLoginType(LoginPointerType loginType) {
		this.loginType = loginType;
	}

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean isAcceptable() {
		return this.acceptable;
	}

	public void setAcceptable(boolean acceptable) {
		this.acceptable = acceptable;
	}

	@Override
	public String toString() {
		return "OAuthLoginInfoImpl [loginType=" + this.loginType + ", id=" + this.id + ", name=" + this.name
		        + ", email=" + this.email + ", acceptable=" + this.acceptable + "]";
	}

}
