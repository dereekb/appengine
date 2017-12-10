package com.dereekb.gae.server.auth.security.model.roles.utility.crud;

import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContext;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.googlecode.objectify.Key;

public interface ModelRoleAttributeInstance<T extends UniqueModel> extends LimitedModelRoleAttributeInstance {

	public Key<T> getKey();

	public ModelRoleSetContext<T> getContext();

}