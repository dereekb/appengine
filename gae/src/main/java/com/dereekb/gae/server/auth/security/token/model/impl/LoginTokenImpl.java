package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.google.common.base.Joiner;

/**
 * {@link LoginToken} implementation.
 *
 * @author dereekb
 */
public class LoginTokenImpl
        implements LoginToken {

	public static final String ROLES_SEPARATOR = ",";

	/**
	 * Optional subject
	 */
	private String subject;

	/**
	 * {@link Login} identifier
	 */
	private Long login;

	/**
	 * {@link LoginPointer} identifier
	 */
	private String loginPointer;

	/**
	 * Set of roles.
	 */
	private Set<Integer> roles = new HashSet<Integer>();

	/**
	 * Time the token was issued.
	 */
	private Date issued;

	/**
	 * Time the token will expire.
	 */
	private Date expiration;

	public LoginTokenImpl() {
		this.issued = new Date();
	}

	@Override
	public String getSubject() {
		String subject = this.subject;

		if (subject == null) {
			subject = (this.login != null) ? this.login.toString() : null;
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

	public Set<Integer> getRoles() {
		return this.roles;
	}

	@Override
	public String getEncodedRoles() {
		Joiner joiner = Joiner.on(ROLES_SEPARATOR).skipNulls();
		String roles = joiner.join(this.roles);
		return (roles.isEmpty()) ? null : roles;
	}

	public void setRoles(Set<Integer> roles) {
		if (roles == null) {
			roles = new HashSet<Integer>();
		}

		this.roles = roles;
	}

	public void setRoles(String roles) throws NumberFormatException {
		Set<Integer> roleSet = new HashSet<Integer>();

		if (roles != null && roles.isEmpty() == false) {
			String[] roleNumbers = roles.split(ROLES_SEPARATOR);

			for (String role : roleNumbers) {
				Integer roleInt = new Integer(role);
				roleSet.add(roleInt);
			}
		}

		this.setRoles(roleSet);
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

	@Override
	public String toString() {
		return "LoginTokenImpl [subject=" + this.subject + ", login=" + this.login + ", loginPointer="
		        + this.loginPointer + ", issued=" + this.issued + ", expiration=" + this.expiration + "]";
	}

}
