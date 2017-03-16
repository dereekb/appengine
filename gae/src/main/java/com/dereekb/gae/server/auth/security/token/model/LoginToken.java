package com.dereekb.gae.server.auth.security.token.model;

import java.util.Date;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRolesBearer;
import com.dereekb.gae.server.auth.security.roles.EncodedRolesBearer;

/**
 * Login token object used as an authentication item.
 *
 * @author dereekb
 *
 */
public interface LoginToken
        extends EncodedRolesBearer, OwnershipRolesBearer {

	/**
	 * Returns the subject of the token. Generally the result of
	 * {@link #getLoginId()}.
	 *
	 * @return {@link String} subject. Never {@code null}.
	 */
	public String getSubject();

	/**
	 * @return {@code true} if it is an Anoynmous login.
	 */
	public boolean isAnonymous();

	/**
	 * Whether or not this is a new user. Generally true if
	 * {{@link #getLoginId()} returns {@code null}, {@link #isAnonymous()}
	 * returns false, and the pointer type is not special.
	 * 
	 * @return {@code true} if it is a new user.
	 */
	public boolean isNewUser();

	/**
	 * Whether or not this token can be used to create a new refresh token.
	 * 
	 * @return {@code true} if can create a new refresh token.
	 */
	public boolean isRefreshAllowed();

	/**
	 * Returns role codes for the user.
	 *
	 * @return {@link Role}. Never {@code null}.
	 */
	public Long getRoles();

	/**
	 * Key of {@link Login} used for logging in.
	 *
	 * @return {@link Long} key of the {@link Login} used for logging in. May be
	 *         {@code null}.
	 */
	public Long getLoginId();

	/**
	 * Key of {@link LoginPointer} used for logging in.
	 *
	 * @return {@link String} key of the {@link LoginPointer} used for logging
	 *         in. May be {@code null}.
	 */
	public String getLoginPointerId();

	/**
	 * Returns the {@link LoginPointerType} used for generating this
	 * {@link LoginToken}.
	 * 
	 * @return {@link LoginPointerType}. Never {@code null}.
	 */
	public LoginPointerType getPointerType();

	/**
	 * Token expiration date.
	 *
	 * @return{@link Date} of expiration. Never {@code null}.
	 */
	public Date getExpiration();

	/**
	 * Token issue date.
	 *
	 * @return {@link Date} token was issued. Never {@code null}.
	 */
	public Date getIssued();

	/**
	 * Whether or not this token has expired.
	 *
	 * @return {@code true} if expired.
	 * @deprecated Token decoding process will throw an error, and the token
	 *             probably shouldn't expire mid-request.
	 */
	@Deprecated
	public boolean hasExpired();

}
