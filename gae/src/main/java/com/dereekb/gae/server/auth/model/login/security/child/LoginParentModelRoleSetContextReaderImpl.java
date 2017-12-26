package com.dereekb.gae.server.auth.model.login.security.child;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.auth.security.model.roles.loader.ModelRoleSetContextGetter;
import com.dereekb.gae.server.auth.security.model.roles.parent.impl.AbstractParentModelRoleSetContextReader;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AbstractParentModelRoleSetContextReader} implementation for
 * {@link LoginOwnedModel} types.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class LoginParentModelRoleSetContextReaderImpl<T extends LoginOwnedModel> extends AbstractParentModelRoleSetContextReader<T, Login> {

	public LoginParentModelRoleSetContextReaderImpl(ModelRoleSetContextGetter<Login> parentGetter) {
		super(parentGetter);
	}

	// MARK: AbstractParentModelRoleSetContextReader
	@Override
	public ModelKey getParentModelKey(LoginOwnedModel child) {
		return child.getLoginOwnerKey();
	}

}
