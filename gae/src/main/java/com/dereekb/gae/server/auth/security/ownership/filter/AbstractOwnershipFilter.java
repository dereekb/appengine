package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResult;
import com.dereekb.gae.utilities.filters.impl.AbstractFactoryFilter;
import com.dereekb.gae.utilities.filters.impl.FilterImpl;

/**
 * {@link Filter} for {@link T} types that uses the
 * {@link LoginSecurityContext}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @param <D>
 *            user type
 */
public abstract class AbstractOwnershipFilter<T, D extends LoginTokenUserDetails<? extends LoginToken>> extends AbstractFactoryFilter<T> {

	// MARK: Internal
	@Override
	public Filter<T> makeFilter() {
		Filter<T> filter = null;

		D principle = this.getUserDetails();

		switch (principle.getUserType()) {
			case ADMINISTRATOR:
				filter = new FilterImpl<T>(FilterResult.PASS);
				break;
			case USER:
				filter = this.makeFilterWithDetails(principle);
				break;
			default:
				filter = new FilterImpl<T>(FilterResult.FAIL);
				break;
		}

		return filter;
	}

	protected abstract D getUserDetails();

	protected abstract Filter<T> makeFilterWithDetails(D details);

}
