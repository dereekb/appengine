package com.dereekb.gae.model.extension.links.system.mutable.impl.assertions;

import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkChange;
import com.dereekb.gae.model.extension.links.system.mutable.MutableLinkDataAssertionDelegate;
import com.dereekb.gae.model.extension.links.system.mutable.exception.ForbiddenLinkChangeException;
import com.dereekb.gae.model.extension.links.system.mutable.exception.MutableLinkChangeException;
import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.model.LoginToken;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;

/**
 * Special {@link MutableLinkDataAssertionDelegate} that only allows the change if the current user is an administrator.
 * 
 * @author dereekb
 *
 * @see ReadOnlyMutableLinkDataAssertionDelegate
 */
public final class AdminOnlyMutableLinkDataAssertionDelegate<T> implements MutableLinkDataAssertionDelegate<T> {

	public static final AdminOnlyMutableLinkDataAssertionDelegate<Object> SINGLETON = new AdminOnlyMutableLinkDataAssertionDelegate<Object>();

	@SuppressWarnings("unchecked")
	public static <T> AdminOnlyMutableLinkDataAssertionDelegate<T> make() {
		return (AdminOnlyMutableLinkDataAssertionDelegate<T>) SINGLETON;
	}

	// MARK: MutableLinkDataAssertionDelegate
	@Override
	public void assertChangeIsAllowed(T model,
	                                  MutableLinkChange change)
	        throws MutableLinkChangeException {
		
		try {
			LoginTokenAuthentication<LoginToken> authentication = LoginSecurityContext.getAuthentication();
			
			if (authentication.getPrincipal().isAdministrator()) {
				return;	// Return, is allowed.
			}
		} catch (NoSecurityContextException e) {
			
		}
		
		throw new ForbiddenLinkChangeException(change);
	}

	@Override
	public String toString() {
		return "AdminOnlyMutableLinkDataAssertionDelegate []";
	}

}
