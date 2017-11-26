package com.dereekb.gae.server.auth.security.model.roles.loader;

import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSet;
import com.dereekb.gae.server.auth.security.model.roles.ModelRoleSetContainer;
import com.dereekb.gae.server.datastore.models.TypedModel;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Anonymous roles context that wraps a {@link UniqueModel} and a {@link ModelRoleSet}.
 *
 * @author dereekb
 */
public interface AnonymousModelRoleSetContext
        extends TypedModel, UniqueModel, ModelRoleSetContainer {}
