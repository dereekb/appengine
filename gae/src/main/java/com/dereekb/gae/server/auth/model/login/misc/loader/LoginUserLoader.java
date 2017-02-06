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

	public T loadUserForLogin(Login login) throws UnavailableModelException, IllegalArgumentException;

	public T loadUserForLogin(ModelKey login) throws UnavailableModelException, IllegalArgumentException;

}
