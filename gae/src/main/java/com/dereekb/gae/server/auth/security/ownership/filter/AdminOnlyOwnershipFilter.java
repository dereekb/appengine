package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.FilterImpl;

/**
 * Administrator only {@link AbstractLoginUserOwnershipFiltersImpl}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class AdminOnlyOwnershipFilter<T> extends AbstractLoginUserOwnershipFiltersImpl<T> {

	@Override
	protected Filter<T> makeFilterWithDetails(LoginTokenUserDetails<LoginToken> details) {
		return new FilterImpl<T>(FilterResult.FAIL);
	}

}
