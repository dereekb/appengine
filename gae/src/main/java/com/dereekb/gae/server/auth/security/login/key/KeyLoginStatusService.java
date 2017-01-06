package com.dereekb.gae.server.auth.security.login.key;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.key.exception.NoKeyLoginPointerException;
import com.dereekb.gae.server.auth.security.login.key.exception.KeyLoginPointerExistsException;
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
	 * @throws NoKeyLoginPointerException thrown if the Login has no associated {@link LoginPointer} for the API key.
	 */
	public Key<LoginPointer> getKeyLoginPointerKey() throws NoKeyLoginPointerException;
	
	/**
	 * Returns the login pointer associated with the account.
	 * 
	 * @return {@link LoginPointer}
	 * 
	 * @throws NoKeyLoginPointerException thrown if the Login has no associated {@link LoginPointer} for the API key.
	 */
	public LoginPointer getKeyLoginPointer() throws NoKeyLoginPointerException;

	/**
	 * Enables API logins for the {@link Login}.
	 * 
	 * @return {@link LoginPointer} created as a result of this function.
	 * 
	 * @throws KeyLoginPointerExistsException thrown if the login pointer already exists.
	 */
	public LoginPointer enable() throws KeyLoginPointerExistsException;
	
	//NOTE: Add Disable?
	
}
