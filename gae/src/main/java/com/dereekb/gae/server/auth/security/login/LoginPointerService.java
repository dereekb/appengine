package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
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
	 */
	public LoginPointer getOrCreateLoginPointer(ModelKey key,
	                                            LoginPointer template);

	/**
	 * Retrieves the {@link LoginPointer}.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @return {@link LoginPointer} if it exists, or {@code null}.
	 */
	public LoginPointer getLoginPointer(ModelKey key);

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

}
