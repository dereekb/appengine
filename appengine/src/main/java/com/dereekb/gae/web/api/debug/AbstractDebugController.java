package com.dereekb.gae.web.api.debug;

import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

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

}
