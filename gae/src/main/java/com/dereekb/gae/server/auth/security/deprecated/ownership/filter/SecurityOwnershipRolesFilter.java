package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.deprecated.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.owner.OwnedModel;
import com.dereekb.gae.utilities.filters.Filter;

/**
 * {@link Filter} for {@link OwnedModel} types that uses the
 * {@link LoginSecurityContext}.
 *
 * @author dereekb
 *
 */
@Deprecated
public class SecurityOwnershipRolesFilter extends AbstractLoginUserOwnershipFiltersImpl<OwnedModel> {

	// MARK: Internal
	@Override
	protected Filter<OwnedModel> makeFilterWithDetails(LoginTokenUserDetails<LoginToken> details) {
		LoginToken token = details.getLoginToken();
		OwnershipRoles ownershipRoles = token.getOwnershipRoles();
		String ownerId = ownershipRoles.getOwnerId();
		return new OwnerIdFilter(ownerId);
	}

}
