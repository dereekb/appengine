package com.dereekb.gae.server.auth.security.token.model;

import java.util.Date;

/**
 * Login token object
 *
 * @author dereekb
 *
 */
public class LoginToken {

	/**
	 * Login this token is associated with.
	 */
	private Long login;

	/**
	 * Login pointer used for logging in.
	 */
	private String loginPointer;

	/**
	 * Token expiration date.
	 */
	private Date expiration;

	public LoginToken() {
		this.expiration = new Date();
	}

	public Long getLogin() {
		return this.login;
	}

	public void setLogin(Long login) {
		this.login = login;
	}

	public String getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(String loginPointer) {
		this.loginPointer = loginPointer;
	}

	public Date getExpiration() {
		return this.expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public boolean hasExpired() {
		return new Date().after(this.expiration);
	}

}
