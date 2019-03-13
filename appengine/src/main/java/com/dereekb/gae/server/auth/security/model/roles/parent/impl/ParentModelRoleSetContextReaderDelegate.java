package com.dereekb.gae.server.auth.security.model.roles.parent.impl;

import com.dereekb.gae.server.auth.security.model.roles.parent.ParentModelRoleSetContextReader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ParentModelRoleSetContextReader} delegate.
 *
 * @author dereekb
 *
 * @Deprecated {@link ParentModelRoleSetContextReader} is deprecated.
 *
 * @param <T>
 *            model type
 */
@Deprecated
public interface ParentModelRoleSetContextReaderDelegate<T> {

	/**
	 * Returns the parent's type.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	@Deprecated
	public String getParentType();

	/**
	 * Returns the parent's model key.
	 *
	 * @param child
	 *            Child. Never {@code null}.
	 * @return {@link ModelKey}. May be {@code null}.
	 */
	public ModelKey getParentModelKey(T child);

}
