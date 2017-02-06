package com.dereekb.gae.server.auth.security.login;

import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginPointerRegisteredException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for registering a new {@link Login} for a {@link LoginPointer}.
 * 
 *
 * @author dereekb
 *
 */
public interface LoginRegisterService {

	/**
	 * Creates and registers a new {@link Login} for the {@link LoginPointer}.
	 *
	 * @param pointer
	 *            {@link LoginPointer}. Never {@code null}.
	 * @return {@link Login} created.
	 * @throws LoginExistsException
	 *             Thrown if the {@link LoginPointer} already has a
	 *             {@link Login} set.
	 * @throws LoginRegistrationRejectedException
	 *             Thrown otherwise if the request is rejected.
	 */
	public Login register(LoginPointer pointer) throws LoginExistsException, LoginRegistrationRejectedException;

	/**
	 * Registers/links multiple {@link LoginPointer} values to a {@link Login}.
	 *
	 * @param loginKey
	 *            {@link ModelKey} of the {@link Login}. Never {@code null}.
	 * @param loginPointers
	 *            {@link Set} of {@link ModelKey} values for
	 *            {@link LoginPointers}.
	 * @throws LoginPointerRegisteredException
	 * @throws AtomicOperationException
	 */
	public void registerPointersToLogin(ModelKey loginKey,
	                                    Set<String> loginPointers)
	        throws LoginPointerRegisteredException,
	            AtomicOperationException;

}
