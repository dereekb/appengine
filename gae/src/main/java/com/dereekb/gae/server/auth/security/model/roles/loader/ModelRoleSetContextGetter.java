package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.auth.security.model.context.exception.NoModelContextRolesGrantedException;
import com.dereekb.gae.server.datastore.SimpleGetter;
import com.dereekb.gae.server.datastore.SimpleKeyGetter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link SimpleGetter} for {@link ModelRoleSetContext} values.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public interface ModelRoleSetContextGetter<T extends UniqueModel>
        extends SimpleKeyGetter<ModelRoleSetContext<T>>, AnonymousModelRoleSetContextGetter {

	/**
	 * {@inheritDoc}
	 *
	 * @throws NoModelContextRolesGrantedException
	 *             if no roles are to be provided to this model.
	 */
	@Override
	public ModelRoleSetContext<T> get(ModelKey key)
	        throws IllegalArgumentException,
	            NoModelContextRolesGrantedException;

}
