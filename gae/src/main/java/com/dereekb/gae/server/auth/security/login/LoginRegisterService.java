package com.dereekb.gae.server.auth.security.login;

import java.util.Set;

import com.dereekb.gae.model.extension.links.exception.LinkException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.auth.security.login.exception.LoginRegistrationRejectedException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for registering a new {@link Login} for a {@link LoginPointer}.
 *
 * @author dereekb
 *
 */
public interface LoginRegisterService {

	/**
	 * Registers a new {@link Login} for the {@link LoginPointer}.
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
	 * Links multiple {@link LoginPointer} values to a {@link Login}.
	 *
	 * @param loginKey
	 *            {@link ModelKey} of the {@link Login}. Never {@code null}.
	 * @param loginPointers
	 *            {@link Set} of {@link ModelKey} values for
	 *            {@link LoginPointers}.
	 * @throws LinkException
	 *             Thrown if a linking exception occurs.
	 */
	public void registerLogins(ModelKey loginKey,
	                           Set<String> loginPointers) throws LinkException;

}
