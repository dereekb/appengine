package com.dereekb.gae.server.auth.security.model.context.service;

import java.util.Set;

import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.server.auth.security.model.context.LoginTokenTypedModelContextSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.AnonymousModelRoleSetContextGetter;
import com.dereekb.gae.server.datastore.models.TypedModel;

/**
 * [@link LoginTokenModelContextService} entry.
 *
 * @author dereekb
 *
 */
public interface LoginTokenModelContextServiceEntry
        extends AnonymousModelRoleSetContextGetter, TypedModel {

	/**
	 * Builds a new {@link LoginTokenTypedModelContextSet} for the input keys.
	 *
	 * @param keys
	 *            {@link Set}. Never {@code null}.
	 * @param atomic
	 *            {@code true} if should be performed atomically.
	 * @return {@link LoginTokenTypedModelContextSet}. Never {@code null}.
	 * @throws AtomicOperationException
	 *             thrown if an atomic operation fails.
	 */
	public LoginTokenTypedModelContextSet makeTypedContextSet(Set<String> keys,
	                                                          boolean atomic)
	        throws AtomicOperationException;

}
