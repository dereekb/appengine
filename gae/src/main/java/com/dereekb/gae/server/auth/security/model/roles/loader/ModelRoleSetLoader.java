package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Used for building roles for a specific model.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetLoader<T extends UniqueModel> {

	/**
	 * Loads the roles for the input model. It is valid for this function to
	 * return an empty {@link ModelRoleSet}.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link ModelRoleSet}. Never {@code null}.
	 * @throws NoModelContextRolesGrantedException
	 *             thrown if the implementation decides that the model should
	 *             not be given roles.
	 */
	public ModelRoleSet loadRolesForModel(T model) throws NoModelContextRolesGrantedException;

}
