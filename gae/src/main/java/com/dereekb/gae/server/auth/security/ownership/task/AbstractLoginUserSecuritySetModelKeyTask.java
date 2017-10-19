package com.dereekb.gae.server.auth.security.ownership.task;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.server.auth.security.token.provider.details.LoginTokenUserDetails;

/**
 * {@link AbstractSecuritySetModelKeyTask} for {@link LoginTokenAuthentication}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractLoginUserSecuritySetModelKeyTask<T> extends AbstractSecuritySetModelKeyTask<T, LoginTokenUserDetails<LoginToken>> {

	public AbstractLoginUserSecuritySetModelKeyTask(boolean failOnNoSecurity) {
		super(failOnNoSecurity);
	}

	// MARK: AbstractSecuritySetModelKeyTask
	@Override
	protected LoginTokenUserDetails<LoginToken> getUserDetails() {
		LoginTokenAuthentication<LoginToken> authentication = LoginSecurityContext.getAuthentication();
		LoginTokenUserDetails<LoginToken> principle = authentication.getPrincipal();
		return principle;
	}

}
