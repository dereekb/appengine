package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ModelRoleSetLoaderBuilder} implementation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetLoaderBuilderImpl<T extends UniqueModel>
        implements ModelRoleSetLoaderBuilder<T> {

	private List<ModelRoleSetLoaderBuilderComponent<T>> components = new ArrayList<ModelRoleSetLoaderBuilderComponent<T>>();

	public ModelRoleSetLoaderBuilderImpl() {}

	public ModelRoleSetLoaderBuilderImpl(List<ModelRoleSetLoaderBuilderComponent<T>> components) {
		this.addComponents(components);
	}

	public void addComponents(List<ModelRoleSetLoaderBuilderComponent<T>> components) {
		for (ModelRoleSetLoaderBuilderComponent<T> component : components) {
			this.component(component);
		}
	}

	// MARK: ModelRoleSetLoaderBuilder
	@Override
	public ModelRoleSetLoaderBuilder<T> component(ModelRoleSetLoaderBuilderComponent<T> component) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ModelRoleSetLoader<T> buildLoader() {
		// TODO Auto-generated method stub
		return null;
	}

	// MARK: ModelRoleSetLoader
	protected static class ModelRoleSetLoaderImpl<T extends UniqueModel>
	        implements ModelRoleSetLoader<T> {

		@Override
		public ModelRoleSet loadRolesForModel(T model) throws NoModelContextRolesGrantedException {
			// TODO Auto-generated method stub
			return null;
		}

		private class LazyModelRoleSet 
		
	}

}
