package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link AnonymousModelRoleSetContext} extension that provides the model.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetContext<T extends UniqueModel>
        extends AnonymousModelRoleSetContext {

	/**
	 * Returns the model in this context.
	 *
	 * @return Model. Never {@code null}.
	 */
	public T getModel();

}
