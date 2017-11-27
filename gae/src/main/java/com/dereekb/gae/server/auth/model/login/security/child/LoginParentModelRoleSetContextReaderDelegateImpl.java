package com.dereekb.gae.server.auth.model.login.security.child;

import com.dereekb.gae.server.auth.model.login.link.LoginLinkSystemBuilderEntry;
import com.dereekb.gae.server.auth.model.login.misc.owned.LoginOwned;
import com.dereekb.gae.server.auth.security.model.roles.parent.impl.ParentModelRoleSetContextReaderDelegate;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link ParentModelRoleSetContextReaderDelegate} implementation for
 * {@link LoginOwned} types.
 *
 * @author dereekb
 *
 */
public final class LoginParentModelRoleSetContextReaderDelegateImpl<T extends LoginOwned>
        implements ParentModelRoleSetContextReaderDelegate<T> {

	private LoginParentModelRoleSetContextReaderDelegateImpl() {}

	public static <T extends LoginOwned> LoginParentModelRoleSetContextReaderDelegateImpl<T> make() {
		return new LoginParentModelRoleSetContextReaderDelegateImpl<T>();
	}

	// MARK: ParentModelRoleSetContextReaderDelegate
	@Override
	public String getParentType() {
		return LoginLinkSystemBuilderEntry.LINK_MODEL_TYPE;
	}

	@Override
	public ModelKey getParentModelKey(LoginOwned child) {
		return child.getLoginOwnerKey();
	}

	@Override
	public String toString() {
		return "LoginParentModelRoleSetContextReaderDelegateImpl []";
	}

}
