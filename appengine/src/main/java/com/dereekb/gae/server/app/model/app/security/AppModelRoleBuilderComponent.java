package com.dereekb.gae.server.app.model.app.security;

import java.util.List;

import com.dereekb.gae.server.app.model.app.App;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.component.AbstractParentChildCrudModelRoleSetLoaderBuilderComponent;
import com.dereekb.gae.server.auth.security.model.roles.loader.builder.impl.granter.ModelRoleGranter;
import com.dereekb.gae.server.auth.security.model.roles.ownership.SecurityContextModelOwnershipChecker;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * {@link AbstractParentChildCrudModelRoleSetLoaderBuilderComponent} for
 * {@link App}.
 *
 * @author dereekb
 *
 */
public class AppModelRoleBuilderComponent extends AbstractParentChildCrudModelRoleSetLoaderBuilderComponent<App> {

	public AppModelRoleBuilderComponent() {
		this(new Checker());
	}

	public AppModelRoleBuilderComponent(SecurityContextModelOwnershipChecker<App> ownershipChecker) {
		super(ownershipChecker);
	}

	@Override
	protected List<ModelRoleGranter<App>> loadGranters() {
		List<ModelRoleGranter<App>> granters = super.loadGranters();

		for (AppModelRole role : AppModelRole.values()) {
			granters.add(this.ownershipRolesGranter.makeGranterForRole(role));
		}

		return granters;
	}

	// MARK: SecurityContextModelOwnershipChecker
	private static final class Checker
	        implements SecurityContextModelOwnershipChecker<App> {

		@Override
		public boolean isOwnedInSecurityContext(App model) throws NoSecurityContextException {
			LoginTokenUserDetails<LoginToken> principal = LoginSecurityContext.getPrincipal();
			ModelKey loginKey = principal.getLoginKey();
			return ModelKey.isEqual(loginKey, model.getLoginOwnerKey());
		}
	}

}
