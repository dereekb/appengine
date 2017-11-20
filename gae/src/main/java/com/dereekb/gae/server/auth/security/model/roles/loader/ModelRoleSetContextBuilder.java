package com.dereekb.gae.server.auth.security.model.roles.loader;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Interface for loading {@link ModelRoleSetContext} values.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetContextBuilder<T extends UniqueModel> {

	/**
	 * Loads a context for the input model.
	 * 
	 * @param model
	 *            Model. Never {@code null}.
	 * @return {@link ModelRoleSetContext}. Never {@code null}.
	 * @throws NoModelContextRolesGrantedException
	 *             thrown if the implementation decides that the model should
	 *             not be given roles.
	 */
	public ModelRoleSetContext<T> loadContext(T model) throws NoModelContextRolesGrantedException;

	/**
	 * Loads contexts for the input models.
	 * <p>
	 * Models with no context are simply ignored from the results.
	 * 
	 * @param models
	 *            {@link Iterable}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelRoleSetContext<T>> loadContexts(Iterable<T> models);

}
