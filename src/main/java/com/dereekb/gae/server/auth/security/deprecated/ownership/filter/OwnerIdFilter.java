package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.datastore.models.owner.OwnedModel;

/**
 * {@link AbstractOwnerKeyFilter} extension for {@link OwnedModel}.
 * 
 * @author dereekb
 *
 */
public class OwnerIdFilter extends AbstractOwnerKeyFilter<String, OwnedModel> {

	public OwnerIdFilter(String ownerId) {
		super(ownerId);
	}

	// MARK: AbstractOwnerKeyFilter
	@Override
	protected String getInputKey(OwnedModel input) {
		return input.getOwnerId();
	}

}
