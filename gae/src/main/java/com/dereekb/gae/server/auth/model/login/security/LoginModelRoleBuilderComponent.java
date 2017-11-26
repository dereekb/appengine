package com.dereekb.gae.server.auth.model.login.security;

import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.AbstractChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AbstractChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link Login}.
 *
 * @author dereekb
 *
 */
public class LoginModelRoleBuilderComponent extends AbstractChildCrudModelRoleSetLoaderBuilderComponent<Login> {

	public LoginModelRoleBuilderComponent() {
		this(new Checker());
	}

	public LoginModelRoleBuilderComponent(SecurityContextModelOwnershipChecker<Login> ownershipChecker) {
		super(ownershipChecker);
	}

	// MARK: SecurityContextModelOwnershipChecker
	private static final class Checker
	        implements SecurityContextModelOwnershipChecker<Login> {

		@Override
		public boolean isOwnedInSecurityContext(Login model) throws NoSecurityContextException {
			LoginTokenUserDetails<LoginToken> principal = LoginSecurityContext.getPrincipal();
			ModelKey loginKey = principal.getLoginKey();
			return ModelKey.isEqual(loginKey, model.getModelKey());
		}
	}

}
