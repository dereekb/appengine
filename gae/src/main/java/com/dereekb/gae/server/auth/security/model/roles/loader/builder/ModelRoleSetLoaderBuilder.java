package com.dereekb.gae.server.auth.security.model.roles.loader.builder;

import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Utility for building together a {@link ModelRoleSetLoader}.
 * 
 * @author dereekb
 *
 */
public interface ModelRoleSetLoaderBuilder<T extends UniqueModel> {

	/**
	 * Adds the builder component.
	 * 
	 * @param component
	 *            {@link ModelRoleSetLoaderBuilderComponent}. Never
	 *            {@code null}.
	 */
	public ModelRoleSetLoaderBuilder<T> component(ModelRoleSetLoaderBuilderComponent<T> component);

	/**
	 * Compiles/builds the loader.
	 * 
	 * @return {@link ModelRoleSetLoader}. Never {@code null}.
	 */
	public ModelRoleSetLoader<T> buildLoader();

}
