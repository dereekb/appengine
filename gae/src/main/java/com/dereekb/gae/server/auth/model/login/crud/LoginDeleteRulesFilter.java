package com.dereekb.gae.server.auth.model.login.crud;

import com.dereekb.gae.model.extension.links.descriptor.filter.UniqueDescribedModelFilter;
import com.dereekb.gae.server.auth.model.login.Login;
import com.dereekb.gae.utilities.filters.AbstractFilter;
import com.dereekb.gae.utilities.filters.FilterResult;

/**
 * Filter that returns {@link FilterResult#PASS} for deletable {@link Login}.
 * <p>
 * Login values are not deletable if they have any pointers still linked, and if
 * their roles are not empty.
 *
 * @author dereekb
 *
 */
public class LoginDeleteRulesFilter extends AbstractFilter<Login> {

	private static final UniqueDescribedModelFilter DESCRIBED_FILTER = new UniqueDescribedModelFilter(false);

	@Override
	public FilterResult filterObject(Login object) {
		return DESCRIBED_FILTER.filterObject(object).and(object.getPointers().isEmpty() && object.getRoles() == 0L);
	}

}
