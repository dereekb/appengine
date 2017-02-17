package com.dereekb.gae.utilities.gae;

import com.google.appengine.api.utils.SystemProperty;

public class GoogleAppEngineUtility {

	public static boolean isDevelopmentEnvironment() {
		return (SystemProperty.environment.value() == SystemProperty.Environment.Value.Development);
	}

	public static boolean isProductionEnvironment() {
		return (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production);
	}

}
