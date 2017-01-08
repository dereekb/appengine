package com.dereekb.gae.server.auth.security.login.key;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginUnavailableException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginExistsException;
import com.googlecode.objectify.Key;

/**
 * Service for a specific {@link Login} for authenticating.
 * 
 * @author dereekb
 */
public interface KeyLoginStatusService {
	
	/**
	 * Returns the {@link Login} context.
	 * 
	 * @return {@link Login}. Never {@code null}.
	 */
	public Login getLogin();
	
	/**
	 * Returns the login pointer key.
	 * 
	 * @return {@link Key} for the {@link LoginPointer}. Never {@code null}.
	 * 
	 * @throws KeyLoginUnavailableException thrown if the Login has no associated {@link LoginPointer} for the API key.
	 */
	public Key<LoginPointer> getKeyLoginPointerKey() throws KeyLoginUnavailableException;
	
	/**
	 * Returns the login pointer associated with the account.
	 * 
	 * @return {@link LoginPointer}
	 * 
	 * @throws KeyLoginUnavailableException thrown if the Login has no associated {@link LoginPointer} for the API key.
	 */
	public LoginPointer getKeyLoginPointer() throws KeyLoginUnavailableException;

	/**
	 * Enables API logins for the {@link Login}.
	 * 
	 * @return {@link LoginPointer} created as a result of this function.
	 * 
	 * @throws KeyLoginExistsException thrown if the login pointer already exists.
	 */
	public LoginPointer enable() throws KeyLoginExistsException;
	
	//NOTE: Add Disable?
	
}
