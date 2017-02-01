package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.ownership.impl.OwnershipRolesImpl;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.utilities.time.DateUtility;

/**
 * {@link LoginToken} implementation.
 *
 * @author dereekb
 */
public class LoginTokenImpl
        implements LoginToken {

	private static final Long DEFAULT_ROLES = 0L;

	/**
	 * Optional subject
	 */
	private String subject;

	/**
	 * Anonymous login or not.
	 */
	private boolean anonymous = false;

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
	private Long roles = DEFAULT_ROLES;

	/**
	 * Time the token was issued.
	 */
	private Date issued;

	/**
	 * Time the token will expire.
	 */
	private Date expiration;

	/**
	 * Pointer type.
	 */
	private LoginPointerType pointerType;

	/**
	 * Ownership roles.
	 */
	private OwnershipRoles ownershipRoles;

	public LoginTokenImpl() {
		this(LoginPointerType.ANONYMOUS);
	}

	public LoginTokenImpl(LoginPointerType pointerType) throws IllegalArgumentException {
		this.setPointerType(pointerType);
		this.setIssued(new Date());
		this.setOwnershipRoles(null);
	}

	public LoginTokenImpl(LoginToken loginToken) throws IllegalArgumentException {
		if (loginToken == null) {
			throw new IllegalArgumentException("LoginToken cannot be null.");
		}

		this.setSubject(loginToken.getSubject());
		this.setAnonymous(loginToken.isAnonymous());
		this.setLogin(loginToken.getLoginId());
		this.setLoginPointer(loginToken.getLoginPointerId());
		this.setRoles(loginToken.getEncodedRoles());
		this.setIssued(loginToken.getIssued());
		this.setExpiration(loginToken.getExpiration());
		this.setPointerType(loginToken.getPointerType());
		this.setOwnershipRoles(loginToken.getOwnershipRoles());
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
	public boolean isAnonymous() {
		return this.anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	@Override
	public Long getLoginId() {
		return this.login;
	}

	public void setLogin(Long login) {
		this.login = login;
	}

	@Override
	public String getLoginPointerId() {
		return this.loginPointer;
	}

	public void setLoginPointer(String loginPointer) {
		this.loginPointer = loginPointer;
	}

	@Override
	public Long getRoles() {
		return this.roles;
	}

	@Override
	public Long getEncodedRoles() {
		return this.roles;
	}

	public void setRoles(Long roles) {
		this.roles = (roles != null) ? roles : DEFAULT_ROLES;
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

	/**
	 * Sets the expiration time in milliseconds from the issued time.
	 * 
	 * @param milliseconds
	 *            {@link Long}. Never {@code null}.
	 */
	public void setExpiration(Long milliseconds) {
		Date expirationDate = DateUtility.getDateIn(this.issued, milliseconds);
		this.setExpiration(expirationDate);
	}

	/**
	 * Sets the expiration time in milliseconds from the current time.
	 * 
	 * @param milliseconds
	 *            {@link Long}. Never {@code null}.
	 */
	public void setToExpireIn(Long milliseconds) {
		Date expirationDate = DateUtility.getDateIn(milliseconds);
		this.setExpiration(expirationDate);
	}

	@Override
	public boolean hasExpired() {
		return new Date().after(this.expiration);
	}

	@Override
	public LoginPointerType getPointerType() {
		return this.pointerType;
	}

	public void setPointerType(LoginPointerType pointerType) {
		this.pointerType = pointerType;
	}

	@Override
	public OwnershipRoles getOwnershipRoles() {
		return this.ownershipRoles;
	}

	public void setOwnershipRoles(OwnershipRoles ownershipRoles) {
		if (ownershipRoles == null) {
			ownershipRoles = new OwnershipRolesImpl();
		}

		this.ownershipRoles = ownershipRoles;
	}

	public void makeOwnershipRoles(String ownerId) {
		this.ownershipRoles = new OwnershipRolesImpl(ownerId);
	}

	@Override
	public String toString() {
		return "LoginTokenImpl [subject=" + this.subject + ", login=" + this.login + ", loginPointer="
		        + this.loginPointer + ", issued=" + this.issued + ", expiration=" + this.expiration + "]";
	}

}
