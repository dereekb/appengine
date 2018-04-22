package com.dereekb.gae.server.auth.security.login;

import com.dereekb.gae.server.auth.model.pointer.LoginPointer;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used for formatting {@link LoginPointer} ids from input usernames.
 *
 * @author dereekb
 *
 */
public interface LoginPointerKeyFormatter {

	/**
	 * Returns the formatted username as a key.
	 *
	 * @param username
	 *            {@link String}. Never {@code null}.
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public ModelKey getKeyForUsername(String username);

	/**
	 * Returns the formatted username.
	 *
	 * @param username
	 *            {@link String}. Never {@code null}.
	 * @return {@link String}. Never {@code null}.
	 */
	public String getIdForUsername(String username);

}
