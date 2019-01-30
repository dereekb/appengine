package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.auth.security.model.roles.loader.impl.EmptyAnonymousModelRoleSetContextGetter;

/**
 * Service for retrieving {@link AnonymousModelRoleSetContextGetter} instances.
 *
 * @author dereekb
 *
 */
public interface AnonymousModelRoleSetContextService {

	/**
	 * Returns a {@link AnonymousModelRoleSetContext} for the type.
	 * <p>
	 * If the type does not exist or is not supported, a
	 * {@link EmptyAnonymousModelRoleSetContextGetter} or similar type is
	 * returned.
	 *
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @return {@link AnonymousModelRoleSetContext}. Never {@code null}.
	 */
	public AnonymousModelRoleSetContextGetter getterForType(String type);

}
