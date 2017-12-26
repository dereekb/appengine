package com.dereekb.gae.server.auth.model.login.security.child;

import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwnedModel;
import com.dereekb.gae.server.auth.security.model.roles.parent.impl.ParentModelRoleSetContextReaderDelegate;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ParentModelRoleSetContextReaderDelegate} implementation for
 * {@link LoginOwnedModel} types.
 *
 * @author dereekb
 *
 */
@Deprecated
public final class LoginParentModelRoleSetContextReaderDelegateImpl<T extends LoginOwnedModel>
        implements ParentModelRoleSetContextReaderDelegate<T> {

	private LoginParentModelRoleSetContextReaderDelegateImpl() {}

	public static <T extends LoginOwnedModel> LoginParentModelRoleSetContextReaderDelegateImpl<T> make() {
		return new LoginParentModelRoleSetContextReaderDelegateImpl<T>();
	}

	// MARK: ParentModelRoleSetContextReaderDelegate
	@Override
	public String getParentType() {
		return LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE;
	}

	@Override
	public ModelKey getParentModelKey(LoginOwnedModel child) {
		return child.getLoginOwnerKey();
	}

	@Override
	public String toString() {
		return "LoginParentModelRoleSetContextReaderDelegateImpl []";
	}

}
