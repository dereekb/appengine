package com.dereekb.gae.utilities.gae;

import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.utils.SystemProperty.Environment.Value;

public class GoogleAppEngineUtility {

	/**
	 * Returns true only if the environment is undefined.
	 */
	public static boolean isUnitTestEnvironment() {
		Value value = SystemProperty.environment.value();
		return value == null;
	}

	/**
	 * Returns true during unit tests and production.
	 */
	public static boolean isDevelopmentEnvironment() {
		Value value = SystemProperty.environment.value();
		return (value != SystemProperty.Environment.Value.Production);
	}

	public static boolean isProductionEnvironment() {
		return !isDevelopmentEnvironment();
	}

	public static Environment getEnvironment() {
		Value value = SystemProperty.environment.value();
		Environment environment;

		if (value == null) {
			environment = Environment.UNIT_TESTING;
		} else if (value == SystemProperty.Environment.Value.Development) {
			environment = Environment.DEVELOPMENT;
		} else {
			environment = Environment.PRODUCTION;
		}

		return environment;
	}

	public static enum Environment {

		UNIT_TESTING,

		DEVELOPMENT,

		PRODUCTION

	}

}
