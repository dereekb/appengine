package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;

/**
 * {@link LoginToken} implementation.
 *
 * @author dereekb
 *
 */
public class LoginTokenImpl
        implements LoginToken {

	private String subject;

	private Long login;

	private String loginPointer;

	private Date issued;

	private Date expiration;

	public LoginTokenImpl() {
		this.issued = new Date();
	}

	@Override
	public String getSubject() {
		String subject = this.subject;

		if (subject == null) {
			subject = this.login.toString();
		}

		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
    public Long getLogin() {
		return this.login;
	}

	public void setLogin(Long login) {
		this.login = login;
	}

	@Override
    public String getLoginPointer() {
		return this.loginPointer;
	}

	public void setLoginPointer(String loginPointer) {
		this.loginPointer = loginPointer;
	}

	@Override
    public Date getIssued() {
		return this.issued;
	}

	public void setIssued(Date issued) {
		this.issued = issued;
	}

	@Override
    public Date getExpiration() {
		return this.expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	@Override
    public boolean hasExpired() {
		return new Date().after(this.expiration);
	}

}
