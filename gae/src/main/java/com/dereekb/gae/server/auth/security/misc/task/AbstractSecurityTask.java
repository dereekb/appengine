package com.dereekb.gae.server.auth.security.misc.task;

import com.dereekb.gae.server.auth.security.context.LoginSecurityContext;
import com.dereekb.gae.server.auth.security.context.exception.NoSecurityContextException;
import com.dereekb.gae.server.auth.security.token.provider.LoginTokenAuthentication;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

public abstract class AbstractSecurityTask {

	/**
	 * Wraps the {@link LoginSecurityContext#getAuthentication()} function in a
	 * try/catch for a {@link Task}.
	 * 
	 * @return {@link LoginTokenAuthentication}. Never {@code null}.
	 * @throws FailedTaskException
	 *             if no authentication available.
	 */
	public static LoginTokenAuthentication getAuthentication() throws FailedTaskException {
		try {
			LoginTokenAuthentication authentication = LoginSecurityContext.getAuthentication();
			return authentication;
		} catch (NoSecurityContextException e) {
			throw new FailedTaskException(e);
		}
	}

}
