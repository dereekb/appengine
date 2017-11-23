package com.dereekb.gae.server.auth.security.model.context;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSetContainer;
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
        extends TypedModel, UniqueModel, ModelRoleSetContainer {}
