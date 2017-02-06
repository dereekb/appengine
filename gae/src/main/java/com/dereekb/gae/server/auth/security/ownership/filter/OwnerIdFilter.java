package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.datastore.models.owner.OwnedModel;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFilter;
import com.dereekb.gae.utilities.filters.impl.DelegatedAbstractFilter;

/**
 * Filters {@link OwnedModel}
 * 
 * @author dereekb
 *
 */
public class OwnerIdFilter extends DelegatedAbstractFilter<OwnedModel> {

	public OwnerIdFilter(String ownerId) {
		this.setOwnerId(ownerId);
	}

	public void setOwnerId(String ownerId) {
		if (ownerId == null) {
			super.setFilter(new NullOwnerStringFilter());
		} else {
			super.setFilter(new SetOwnerStringFilter(ownerId));
		}
	}

	// MARK: Internal
	private class NullOwnerStringFilter extends AbstractFilter<OwnedModel> {

		@Override
		public FilterResult filterObject(OwnedModel object) {
			return FilterResult.withBoolean(object.getOwnerId() == null);
		}

	}

	private class SetOwnerStringFilter extends AbstractFilter<OwnedModel> {

		private final String ownerId;

		public SetOwnerStringFilter(String ownerId) {
			this.ownerId = ownerId;
		}

		@Override
		public FilterResult filterObject(OwnedModel object) {
			return FilterResult.withBoolean(this.ownerId.equals(object.getOwnerId()));
		}

	}

}
