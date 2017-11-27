package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.datastore.SimpleGetter;
import com.dereekb.gae.server.datastore.SimpleKeyGetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link SimpleGetter} for {@link ModelRoleSetContext} values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetContextGetter<T extends UniqueModel>
        extends SimpleKeyGetter<ModelRoleSetContext<T>>, AnonymousModelRoleSetContextGetter {}
