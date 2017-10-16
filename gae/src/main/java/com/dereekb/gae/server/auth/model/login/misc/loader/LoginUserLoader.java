package com.dereekb.gae.server.auth.model.login.misc.loader;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Interface for loading a "user" model linked to a {@link Login}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface LoginUserLoader<T> {

	/**
	 * Loads the user for the input {@link Login}.
	 * 
	 * @param login
	 * @return
	 * @throws UnavailableModelException
	 * @throws IllegalArgumentException
	 */
	public T loadUserForLogin(Login login) throws UnavailableModelException, IllegalArgumentException;

	/**
	 * Loads the user for the {@link Login} corresponding to the input
	 * {@link ModelKey}.
	 * 
	 * @param login
	 * @return
	 * @throws UnavailableModelException
	 * @throws IllegalArgumentException
	 */
	public T loadUserForLogin(ModelKey login) throws UnavailableModelException, IllegalArgumentException;

}
