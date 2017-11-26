package com.dereekb.gae.server.auth.security.model.roles.loader.impl;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;

/**
 * {@link ModelRoleSetContext} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetContextImpl<T extends UniqueModel> extends HandlerPair<T, ModelRoleSet>
        implements ModelRoleSetContext<T> {

	private String modelType;

	public ModelRoleSetContextImpl(String modelType, T key, ModelRoleSet object) {
		super(key, object);
		this.modelType = modelType;
	}

	// MARK: ModelRoleSetContext
	@Override
	public T getModel() {
		return this.key;
	}

	@Override
	public ModelRoleSet getRoleSet() {
		return this.object;
	}

	// MARK: TypedModel
	@Override
	public String getModelType() {
		return this.modelType;
	}

	// MARK: UniqueModel
	@Override
	public ModelKey getModelKey() {
		return this.key.getModelKey();
	}

	@Override
	public ModelKey keyValue() {
		return this.key.keyValue();
	}

	@Override
	public String toString() {
		return "ModelRoleSetContextImpl [getModel()=" + this.getModel() + ", getRoles()=" + this.getRoleSet() + "]";
	}
}
