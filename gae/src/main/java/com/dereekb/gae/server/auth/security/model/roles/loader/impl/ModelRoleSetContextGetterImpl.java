package com.dereekb.gae.server.auth.security.model.roles.loader.impl;

import java.util.List;

import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextGetter;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.SimpleKeyGetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ModelRoleSetContextGetter} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetContextGetterImpl<T extends UniqueModel> extends ModelRolesContextBuilderImpl<T>
        implements ModelRoleSetContextGetter<T>, ModelRoleSetContextService<T> {

	private SimpleKeyGetter<T> getter;

	public ModelRoleSetContextGetterImpl(ModelRoleSetLoader<T> roleSetLoader, SimpleKeyGetter<T> getter) {
		super(roleSetLoader);
		this.setGetter(getter);
	}

	public SimpleKeyGetter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(SimpleKeyGetter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	// MARK: ModelRoleSetContextGetter
	@Override
	public ModelRoleSetContext<T> get(ModelKey key) throws IllegalArgumentException {
		T model = this.getter.get(key);

		if (model != null) {
			return this.loadContext(model);
		} else {
			return null;
		}
	}

	@Override
	public List<ModelRoleSetContext<T>> getWithKeys(Iterable<ModelKey> keys) {
		List<T> models = this.getter.getWithKeys(keys);
		return this.loadContexts(models);
	}

	@Override
	public String toString() {
		return "ModelRoleSetContextGetterImpl [getter=" + this.getter + ", getRoleSetLoader()="
		        + this.getRoleSetLoader() + "]";
	}

}
