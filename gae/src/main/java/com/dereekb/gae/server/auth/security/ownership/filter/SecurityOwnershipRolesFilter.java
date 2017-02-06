package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.ownership.OwnershipRoles;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.server.datastore.models.owner.OwnedModel;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterDelegate;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.dereekb.gae.utilities.filters.impl.FilterImpl;

/**
 * {@link Filter} for {@link OwnedModel} types that uses the
 * {@link LoginSecurityContext}.
 * 
 * @author dereekb
 *
 */
public class SecurityOwnershipRolesFilter
        implements Filter<OwnedModel> {

	// MARK: Filter
	@Override
	public FilterResult filterObject(OwnedModel object) {
		return makeFilter().filterObject(object);
	}

	@Override
	public FilterResults<OwnedModel> filterObjects(Iterable<? extends OwnedModel> objects) {
		return makeFilter().filterObjects(objects);
	}

	@Override
	public <W> FilterResults<W> filterObjectsWithDelegate(Iterable<? extends W> sources,
	                                                      FilterDelegate<OwnedModel, W> delegate) {
		return makeFilter().filterObjectsWithDelegate(sources, delegate);
	}

	// MARK: Internal
	private static Filter<OwnedModel> makeFilter() {
		Filter<OwnedModel> filter = null;
		LoginTokenAuthentication authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails principle = authentication.getPrincipal();

		if (principle.isAdministrator()) {
			filter = new FilterImpl<OwnedModel>(FilterResult.PASS);
		} else if (principle.isAnonymous()) {
			filter = new FilterImpl<OwnedModel>(FilterResult.FAIL);
		} else {
			LoginToken token = principle.getLoginToken();
			OwnershipRoles ownershipRoles = token.getOwnershipRoles();
			String ownerId = ownershipRoles.getOwnerId();
			filter = new OwnerIdFilter(ownerId);
		}

		return filter;
	}

}
