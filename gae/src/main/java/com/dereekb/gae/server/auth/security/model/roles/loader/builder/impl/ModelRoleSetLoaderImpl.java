package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.Collection;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.auth.security.model.roles.ModelRole;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponentSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoadingSet;
import com.dereekb.gae.server.datastore.models.UniqueModel;

// MARK: ModelRoleSetLoader
public class ModelRoleSetLoaderImpl<T extends UniqueModel>
        implements ModelRoleSetLoader<T> {

	private ModelRoleSetLoaderBuilderComponentSet<T> set;

	public ModelRoleSetLoaderImpl(ModelRoleSetLoaderBuilderComponentSet<T> set) {
		this.setSet(set);
	}

	public ModelRoleSetLoaderBuilderComponentSet<T> getSet() {
		return this.set;
	}

	public void setSet(ModelRoleSetLoaderBuilderComponentSet<T> set) {
		if (set == null) {
			throw new IllegalArgumentException("set cannot be null.");
		}

		this.set = set;
	}

	// MARK: ModelRoleSetLoader
	@Override
	public ModelRoleSet loadRolesForModel(T model) throws NoModelContextRolesGrantedException {
		return new LazyModelRoleSet(model);
	}

	private class LazyModelRoleSet
	        implements ModelRoleSet {

		private final T model;
		private ModelRoleSetLoadingSet roles;
		private ModelRoleSetLoadingSet unloadedRoleSet;

		public LazyModelRoleSet(T model) {
			super();
			this.model = model;
			this.roles = new ModelRoleSetLoadingSetImpl();
			this.unloadedRoleSet = new ModelRoleSetLoadingSetImpl(ModelRoleSetLoaderImpl.this.set.getAllRoleKeys());
		}

		// MARK: ModelRoleSet
		@Override
		public boolean isEmpty() {
			this.loadRemainingRoles();
			return this.roles.isEmpty();
		}

		@Override
		public boolean hasRole(ModelRole role) {
			if (this.unloadedRoleSet.containsRole(role)) {
				return this.tryLoadRole(role);
			}

			return this.roles.containsRole(role);
		}

		@Override
		public Collection<ModelRole> getRoles() {
			this.loadRemainingRoles();
			return this.roles.getRoles();
		}

		protected void loadRemainingRoles() {
			Collection<ModelRole> roles = this.unloadedRoleSet.getRoles();

			for (ModelRole role : roles) {
				this.tryLoadRole(role);
			}
		}

		protected boolean tryLoadRole(ModelRole role) {
			List<ModelRoleSetLoaderBuilderComponent<T>> components = ModelRoleSetLoaderImpl.this.set
			        .getComponentsForRole(role);

			for (ModelRoleSetLoaderBuilderComponent<T> component : components) {
				component.loadRole(this.model, role, this.roles);
			}

			return this.roles.containsRole(role);
		}

	}

	@Override
	public String toString() {
		return "ModelRoleSetLoaderImpl [set=" + this.set + "]";
	}

}