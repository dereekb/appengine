package com.dereekb.gae.server.auth.security.login;

import java.util.List;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.auth.model.pointer.LoginPointerType;
import com.dereekb.gae.server.auth.security.login.exception.LoginDisabledException;
import com.dereekb.gae.server.auth.security.login.exception.LoginExistsException;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for retrieving and creating {@link LoginPointer} values.
 *
 * @author dereekb
 *
 */
public interface LoginPointerService {

	/**
	 * Returns a {@link LoginPointer} if it exists, or creates a new one.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @param template
	 *            (Optional) {@link LoginPointer}.
	 * @return {@link LoginPointer} if it exists, or {@code null}.
	 *
	 * @throws LoginDisabledException
	 *             if the login exists but is disabled.
	 */
	public LoginPointer getOrCreateLoginPointer(ModelKey key,
	                                            LoginPointer template)
	        throws LoginDisabledException;

	/**
	 * Retrieves the {@link LoginPointer}.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @return {@link LoginPointer} if it exists, or {@code null}.
	 *
	 * @throws LoginDisabledException
	 *             if the login exists but is disabled.
	 */
	public LoginPointer getLoginPointer(ModelKey key) throws LoginDisabledException;

	/**
	 * Creates a new {@link LoginPointer}.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @param template
	 *            (Optional) {@link LoginPointer}.
	 * @return {@link LoginPointer}. Never {@code null}.
	 * @throws LoginExistsException
	 *             thrown if the login exists.
	 */
	public LoginPointer createLoginPointer(ModelKey key,
	                                       LoginPointer template)
	        throws LoginExistsException;

	/**
	 * Changes the verified status for the pointer, and returns it.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @param verified
	 *            new verified state.
	 * @return {@link LoginPointer}. Never {@code null}.
	 * @throws UnavailableModelException
	 */
	public LoginPointer changeVerified(ModelKey key,
	                                   boolean verified)
	        throws UnavailableModelException;

	/**
	 * Returns all login pointers associated with the specified email.
	 *
	 * @param email
	 *            {@link String}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if email is null or empty.
	 *
	 * @throws LoginDisabledException
	 *             if the login exists but is disabled.
	 */
	public List<LoginPointer> findWithEmail(String email) throws LoginDisabledException, IllegalArgumentException;

	/**
	 * Finds the login pointer with the specified type and email.
	 *
	 * @param type
	 *            {@link LoginPointerType}. Never {@code null}.
	 * @param email
	 *            {@link String}. Never {@code null}.
	 * @return {@link LoginPointer}, or {@code null} if it does not exist.
	 * @throws IllegalArgumentException
	 *             thrown if type or email are null.
	 *
	 * @throws LoginDisabledException
	 *             if the login exists but is disabled.
	 */
	public LoginPointer findWithEmail(LoginPointerType type,
	                                  String email)
	        throws LoginDisabledException,
	            IllegalArgumentException;

}
