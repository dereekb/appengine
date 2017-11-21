package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
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
        extends UniqueModel {

	/**
	 * Returns the model in this context.
	 * 
	 * @return Model. Never {@code null}.
	 */
	public T getModel();

	/**
	 * Returns the set of roles.
	 * 
	 * @return {@link ModelSet}. Never {@code null}.
	 */
	public ModelRoleSet getRoleSet();

}
