package com.dereekb.gae.server.auth.security.login;

import org.springframework.security.core.userdetails.UserDetails;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.exception.NoModelKeyException;

/**
 * {@link UserDetails} extension for {@link Login} information.
 *
 * @author dereekb
 *
 */
public interface LoginUserDetails
        extends UserDetails {

	/**
	 * Returns the key of the {@link Login}, if available.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 * @throws NoModelKeyException
	 *             if there is no key available.
	 */
	public ModelKey getLoginKey() throws NoModelKeyException;

	/**
	 * Returns the current {@link Login}, if available.
	 * 
	 * This function is not recommended for use due to the potential overhead
	 * associated with it and the uncertainty of the result.
	 *
	 * @return {@link Login}. May be {@code null}.
	 * @throws UnsupportedOperationException
	 *             if loading the pointer is not supported.
	 */
	public Login getLogin() throws UnsupportedOperationException;

	/**
	 * Returns the key of the {@link LoginPointer}, if available.
	 * 
	 * @return {@link ModelKey}. Never {@code null}.
	 * @throws NoModelKeyException
	 *             if there is no key available.
	 */
	public ModelKey getLoginPointerKey() throws NoModelKeyException;

	/**
	 * Returns the {@link LoginPointer} associated with this instance, if
	 * available.
	 *
	 * This function is not recommended for use due to the potential overhead
	 * associated with it and the uncertainty of the result.
	 * 
	 * @return {@link LoginPointer}. May be {@code null}.
	 * @throws UnsupportedOperationException
	 *             if loading the pointer is not supported.
	 */
	public LoginPointer getLoginPointer() throws UnsupportedOperationException;

}
