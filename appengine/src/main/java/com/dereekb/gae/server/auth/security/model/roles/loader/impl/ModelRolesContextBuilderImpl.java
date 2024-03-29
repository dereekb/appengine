package com.dereekb.gae.server.auth.security.model.roles.loader.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextBuilder;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.impl.TypedModelImpl;

/**
 * {@link ModelRoleSetContextBuilder} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRolesContextBuilderImpl<T extends UniqueModel> extends TypedModelImpl
        implements TypedModel, ModelRoleSetContextBuilder<T>, ModelRoleSetLoader<T> {

	private ModelRoleSetLoader<T> roleSetLoader;

	public ModelRolesContextBuilderImpl(String modelType, ModelRoleSetLoader<T> roleSetLoader) {
		super(modelType);
		this.setRoleSetLoader(roleSetLoader);
	}

	public ModelRoleSetLoader<T> getRoleSetLoader() {
		return this.roleSetLoader;
	}

	public void setRoleSetLoader(ModelRoleSetLoader<T> roleSetLoader) {
		if (roleSetLoader == null) {
			throw new IllegalArgumentException("roleSetLoader cannot be null.");
		}

		this.roleSetLoader = roleSetLoader;
	}

	// MARK: ModelRoleSetLoader
	@Override
	public ModelRoleSet loadRolesForModel(T model) throws NoModelContextRolesGrantedException {
		return this.getRoleSetLoader().loadRolesForModel(model);
	}

	// MARK: ModelRoleSetContextBuilder
	@Override
	public ModelRoleSetContext<T> loadContext(T model) throws NoModelContextRolesGrantedException {
		ModelRoleSet roleSet = this.roleSetLoader.loadRolesForModel(model);
		return new ModelRoleSetContextImpl<T>(this.getModelType(), model, roleSet);
	}

	@Override
	public List<ModelRoleSetContext<T>> loadContexts(Iterable<T> models) {
		List<ModelRoleSetContext<T>> contexts = new ArrayList<ModelRoleSetContext<T>>();

		for (T model : models) {
			try {
				ModelRoleSetContext<T> context = this.loadContext(model);
				contexts.add(context);
			} catch (NoModelContextRolesGrantedException e) {
				// Do nothing.
			}
		}

		return contexts;
	}

	@Override
	public String toString() {
		return "ModelRolesContextBuilderImpl [roleSetLoader=" + this.roleSetLoader + "]";
	}

}
