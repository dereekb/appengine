package com.dereekb.gae.web.api.debug;

import java.util.logging.Level;
import java.util.logging.Logger;

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

	private static final Logger LOGGER = Logger.getLogger(AbstractDebugController.class.getName());

	protected AbstractDebugController() {
		if (!this.isDebugEnabledEnvironment()) {
			LOGGER.log(Level.SEVERE, "Debug Controller initialized in a non-debug environment.");
		}
	}

	protected void assertIsDebugEnvironment() {
		throw new RuntimeException("Not a debug enabled environment.");
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
