package com.dereekb.gae.server.auth.security.ownership.filter;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * {@link AbstractOwnershipFilter} that uses the default available
 * {@link LoginTokenUserDetails}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractLoginUserOwnershipFiltersImpl<T> extends AbstractOwnershipFilter<T, LoginTokenUserDetails<LoginToken>> {

	// AbstractOwnershipFilter
	@Override
	protected LoginTokenUserDetails<LoginToken> getUserDetails() {
		LoginTokenAuthentication<LoginToken> authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails<LoginToken> principle = authentication.getPrincipal();
		return principle;
	}

}
