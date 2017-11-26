package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Service for building a {@link ModelRoleSetContext} for a specific model type.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetContextService<T extends UniqueModel>
        extends ModelRoleSetContextBuilder<T>, ModelRoleSetContextGetter<T>, ModelRoleSetLoader<T> {

}
