package com.dereekb.gae.server.auth.security.model.roles.loader;

import java.util.List;

import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Anonymous getter for {@link AnonymousModelRoleSetContext} values.
 *
 * @author dereekb
 *
 * @see {@link ModelRoleSetContextGetter}
 */
public interface AnonymousModelRoleSetContextGetter
        extends TypedModel {

	/**
	 * Attempts to load the corresponding model for the input key.
	 *
	 * @param key
	 *            {@link ModelKey}. Never {@code null}.
	 * @return {@link AnonymousModelRoleSetContext}. Never {@code null}.
	 * @throws IllegalArgumentException
	 *             thrown if the input key is {@code null}.
	 */
	public AnonymousModelRoleSetContext getAnonymous(ModelKey key) throws IllegalArgumentException;

	/**
	 * Attempts to load contexts for each of the input values.
	 * <p>
	 * Values that do not exist may still be returned, but
	 *
	 * @param keys
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<? extends AnonymousModelRoleSetContext> getAnonymousWithKeys(Iterable<ModelKey> keys);

}
