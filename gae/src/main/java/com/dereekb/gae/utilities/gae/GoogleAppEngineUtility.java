package com.dereekb.gae.utilities.gae;

import java.util.Map;

import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.AppVersion;
import com.dereekb.gae.server.app.model.app.info.impl.AppServiceVersionInfoImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppVersionImpl;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.utils.SystemProperty.Environment.Value;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;

public class GoogleAppEngineUtility {

	public static final String VERSION_JOINER = ".";
	public static final String VERSION_SPLITTER = "\\.";

	// MARK: Environment
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

	public static EnvironmentType getEnvironmentType() {
		Value value = SystemProperty.environment.value();
		EnvironmentType environment;

		if (value == null) {
			environment = EnvironmentType.UNIT_TESTING;
		} else if (value == SystemProperty.Environment.Value.Development) {
			environment = EnvironmentType.DEVELOPMENT;
		} else {
			environment = EnvironmentType.PRODUCTION;
		}

		return environment;
	}

	public static enum EnvironmentType {

		UNIT_TESTING,

		DEVELOPMENT,

		PRODUCTION

	}

	// MARK: Request
	public static final String REQUEST_LOG_ID_ATTR_KEY = "com.google.appengine.runtime.request_log_id";

	public static String getRequestLogId() {
		return (String) getApiEnvironmentAttributes().get(REQUEST_LOG_ID_ATTR_KEY);
	}

	public static Map<String, Object> getApiEnvironmentAttributes() {
		return getApiEnvironment().getAttributes();
	}

	public static Environment getApiEnvironment() {
		return ApiProxy.getCurrentEnvironment();
	}

	public static AppServiceVersionInfo getApplicationInfo() {
		Environment environment = getApiEnvironment();

		String app = environment.getAppId();
		String service = environment.getModuleId();
		String version = environment.getVersionId();

		return new AppServiceVersionInfoImpl(app, service, decodeAppVersion(version));
	}

	public static String getApplicationId() {
		return ApiProxy.getCurrentEnvironment().getAppId();
	}

	public static String getApplicationVersionId() {
		return ApiProxy.getCurrentEnvironment().getVersionId();
	}

	public static String getApplicationEmail() {
		return ApiProxy.getCurrentEnvironment().getEmail();
	}

	// MARK: Version
	public static String encodeAppVersion(AppVersion version) {
		return version.getMajorVersion() + VERSION_JOINER + version.getMinorVersion();
	}

	public static AppVersion decodeAppVersion(String versionString) {
		String[] split = versionString.split(VERSION_SPLITTER);
		return new AppVersionImpl(split[0], split[1]);
	}

	public static String urlForService(String appProjectId,
	                                   String appServiceName,
	                                   String appVersion) {

		// TODO: Complete!

		return "";

		// throw new UnsupportedOperationException("TODO");
	}

}
