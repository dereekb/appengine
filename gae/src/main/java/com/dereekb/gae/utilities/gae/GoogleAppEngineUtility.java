package com.dereekb.gae.utilities.gae;

import com.google.appengine.api.utils.SystemProperty;

public class GoogleAppEngineUtility {

	public static final boolean isDevelopment = (SystemProperty.environment
	        .value() == SystemProperty.Environment.Value.Development);
	public static final boolean isProduction = !isDevelopment;

	public static boolean isDevelopmentEnvironment() {
		return isDevelopment;
	}

	public static boolean isProductionEnvironment() {
		return isProduction;
	}

}
