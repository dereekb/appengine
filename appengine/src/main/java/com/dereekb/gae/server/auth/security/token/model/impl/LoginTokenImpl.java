package com.dereekb.gae.server.auth.security.token.model.impl;

import java.util.Date;
import java.util.Set;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.model.context.encoded.EncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.model.context.encoded.exception.UnavailableEncodedModelContextException;
import com.dereekb.gae.server.auth.security.model.context.encoded.impl.EmptyEncodedLoginTokenModelContextSet;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.utilities.time.DateUtility;

/**
 * {@link LoginToken} implementation.
 *
 * @author dereekb
 */
public class LoginTokenImpl
        implements LoginToken {

	public static final Long DEFAULT_ROLES = 0L;

	/**
	 * Optional subject
	 */
	private String subject;

	/**
	 * Optional app association.
	 */
	private String app;

	/**
	 * {@link Login} identifier
	 */
	private Long login;

	/**
	 * {@link LoginPointer} identifier
	 */
	private String loginPointer;

	/**
	 * Refresh token ability.
	 */
	private boolean refreshAllowed = false;

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
	 * Encoded context info.
	 */
	private EncodedLoginTokenModelContextSet encodedModelContextSet = EmptyEncodedLoginTokenModelContextSet.make();

	public LoginTokenImpl() {
		this(LoginPointerType.ANONYMOUS);
	}

	public LoginTokenImpl(LoginPointerType pointerType) throws IllegalArgumentException {
		this.setPointerType(pointerType);
		this.setIssued(new Date());
	}

	public LoginTokenImpl(LoginToken loginToken) throws IllegalArgumentException {
		this.copyFromLoginToken(loginToken);
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
	public String getApp() {
		return this.app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	@Override
	public boolean isRegistered() {
		return this.login != null;
	}

	@Override
	public boolean isNewUser() {
		return this.login == null && !this.pointerType.isInternalType();
	}

	@Override
	public boolean isAnonymous() {
		return (this.pointerType.equals(LoginPointerType.ANONYMOUS));
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
	public boolean isRefreshAllowed() {
		return this.refreshAllowed;
	}

	public void setRefreshAllowed(boolean refreshAllowed) {
		this.refreshAllowed = refreshAllowed;
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
	public EncodedLoginTokenModelContextSet getEncodedModelContextSet() {
		return this.encodedModelContextSet;
	}

	public void setEncodedModelContextSet(EncodedLoginTokenModelContextSet encodedModelContextSet) {
		if (encodedModelContextSet == null) {
			encodedModelContextSet = EmptyEncodedLoginTokenModelContextSet.make();
		}

		this.encodedModelContextSet = encodedModelContextSet;
	}

	@Override
	public Set<Integer> getEncodedModelContextTypes() {
		return this.encodedModelContextSet.getEncodedModelContextTypes();
	}

	@Override
	public String getEncodedModelTypeContext(Integer encodedType) throws UnavailableEncodedModelContextException {
		return this.encodedModelContextSet.getEncodedModelTypeContext(encodedType);
	}

	// MARK: Copy
	public void copyFromLoginToken(LoginToken loginToken) {
		if (loginToken == null) {
			throw new IllegalArgumentException("LoginToken cannot be null.");
		}

		this.setSubject(loginToken.getSubject());
		this.setApp(loginToken.getApp());
		this.setLogin(loginToken.getLoginId());
		this.setLoginPointer(loginToken.getLoginPointerId());
		this.setRoles(loginToken.getEncodedRoles());
		this.setIssued(loginToken.getIssued());
		this.setExpiration(loginToken.getExpiration());
		this.setPointerType(loginToken.getPointerType());
		this.setRefreshAllowed(loginToken.isRefreshAllowed());
		this.setEncodedModelContextSet(loginToken.getEncodedModelContextSet());
	}

	@Override
	public String toString() {
		return "LoginTokenImpl [subject=" + this.subject + ", app=" + this.app + ", login=" + this.login
		        + ", loginPointer=" + this.loginPointer + ", refreshAllowed=" + this.refreshAllowed + ", roles="
		        + this.roles + ", issued=" + this.issued + ", expiration=" + this.expiration + ", pointerType="
		        + this.pointerType + ", encodedModelContextSet=" + this.encodedModelContextSet + "]";
	}

}
