package com.dereekb.gae.server.auth.security.model.context;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link LoginToken} context that also represents a specific model.
 * 
 * @author dereekb
 *
 */
public interface LoginTokenModelContext
        extends TypedModel, UniqueModel {

	/**
	 * Returns the role set for this model.
	 * 
	 * @return {@link ModelRoleSet}. Never {@code null}.
	 */
	public ModelRoleSet getRoleSet();

}
