package com.dereekb.gae.server.auth.security.token.model;

import java.util.Date;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;

/**
 * Login token object
 *
 * @author dereekb
 *
 */
public interface LoginToken {

	/**
	 * Returns the subject of the token. Generally the result of
	 * {@link #getLogin()}.
	 *
	 * @return {@link String} subject. Never {@code null}.
	 */
	public String getSubject();

	/**
	 *
	 * @return
	 */
	public Long getLogin();

	/**
	 * Key of {@link LoginPointer} used for logging in.
	 *
	 * @return {@link String} key of the {@link LoginPointer} used for logging
	 *         in. May be {@code null}.
	 */
	public String getLoginPointer();

	/**
	 * Token expiration date.
	 *
	 * @return{@link Date} of expiration. Never {@code null}.
	 */
	public Date getExpiration();

	/**
	 * Token issue date.
	 *
	 * @return{@link Date} token was issued. Never {@code null}.
	 */
	public Date getIssued();

	/**
	 * Whether or not this token has expired.
	 *
	 * @return {@code true} if expired.
	 */
	public boolean hasExpired();

}
