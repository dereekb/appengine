package com.dereekb.gae.server.auth.security.model.roles.loader.impl;

import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextService;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetLoader;
import com.dereekb.gae.server.datastore.SimpleKeyGetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link ModelRoleSetContextService} implementation.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ModelRoleSetContextServiceImpl<T extends UniqueModel> extends ModelRoleSetContextGetterImpl<T>
        implements ModelRoleSetContextService<T> {

	public ModelRoleSetContextServiceImpl(ModelRoleSetLoader<T> roleSetLoader, SimpleKeyGetter<T> getter) {
		super(roleSetLoader, getter);
	}

}
