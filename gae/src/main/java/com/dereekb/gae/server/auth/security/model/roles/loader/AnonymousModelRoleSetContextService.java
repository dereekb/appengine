package com.dereekb.gae.server.auth.security.model.roles.loader;

/**
 * Service for retrieving {@link AnonymousModelRoleSetContextGetter} instances.
 *
 * @author dereekb
 *
 */
public interface AnonymousModelRoleSetContextService {

	/**
	 * Returns a {@link AnonymousModelRoleSetContext} for the type.
	 *
	 * @param type
	 *            {@link String}. Never {@code null}.
	 * @return {@link AnonymousModelRoleSetContext}. Never {@code null}.
	 */
	public AnonymousModelRoleSetContextGetter getterForType(String type);

}
