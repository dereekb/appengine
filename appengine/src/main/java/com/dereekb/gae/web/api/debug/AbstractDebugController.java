package com.dereekb.gae.web.api.debug;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;
import com.dereekb.gae.utilities.task.Task;

/**
 * Abstract controller that only allowed being used in debug environments.
 *
 * @author dereekb
 *
 */
public class AbstractDebugController {

	protected AbstractDebugController() {
		if (!this.isDebugEnabledEnvironment()) {
			throw new RuntimeException("Not a debug enabled environment.");
		}
	}

	protected boolean isDebugEnabledEnvironment() {
		return !GoogleAppEngineUtility.isProductionEnvironment();
	}

	/**
	 * Changes the security context to the specified authentication for a task,
	 * then switches it back.
	 */
	protected void changeToAuthenticationForTask(Authentication authentication,
	                                             Task<?> task) {
		Authentication current = SecurityContextHolder.getContext().getAuthentication();
		SecurityContextHolder.getContext().setAuthentication(authentication);

		task.doTask(null);

		SecurityContextHolder.getContext().setAuthentication(current);
	}

}
