package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSetContainer;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Roles context that wraps a {@link UniqueModel} and a {@link ModelRoleSet}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetContext<T extends UniqueModel>
        extends UniqueModel, ModelRoleSetContainer {

	/**
	 * Returns the model in this context.
	 *
	 * @return Model. Never {@code null}.
	 */
	public T getModel();

}
