package com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.ModelRoleSetLoaderBuilderComponentSet;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ModelRoleSetLoaderBuilder} implementation.
 * <p>
 * Facilitates as much lazy loading as possible with it's implementations.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetLoaderBuilderImpl<T extends UniqueModel>
        implements ModelRoleSetLoaderBuilder<T> {

	private ModelRoleSetLoaderBuilderComponentSet<T> set = new ModelRoleSetLoaderBuilderComponentSetImpl<T>();

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
		this.set.add(component);
		return this;
	}

	@Override
	public ModelRoleSetLoader<T> buildLoader() {
		ModelRoleSetLoaderBuilderComponentSet<T> copy = this.set.copySet();
		return new ModelRoleSetLoaderImpl<T>(copy);
	}

}
