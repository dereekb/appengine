package com.dereekb.gae.server.auth.model.key.security;

import com.dereekb.gae.server.auth.model.key.LoginKey;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginKeyModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<LoginKey> {

	public LoginKeyModelRoleBuilderComponent() {
		this(new Checker());
	}

	public LoginKeyModelRoleBuilderComponent(SecurityContextModelOwnershipChecker<LoginKey> ownershipChecker) {
		super(ownershipChecker);
	}

	// MARK: SecurityContextModelOwnershipChecker
	private static final class Checker
	        implements SecurityContextModelOwnershipChecker<LoginKey> {

		@Override
		public boolean isOwnedInSecurityContext(Login model) throws NoSecurityContextException {
			LoginTokenUserDetails<LoginToken> principal = LoginSecurityContext.getPrincipal();
			ModelKey loginKey = principal.getLoginKey();
			return ModelKey.isEqual(loginKey, model.getModelKey());
		}
	}

}
