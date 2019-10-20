package com.dereekb.gae.utilities.gae;

import java.util.Map;

import com.dereekb.gae.server.app.model.app.info.AppServiceVersionInfo;
import com.dereekb.gae.server.app.model.app.info.AppVersion;
import com.dereekb.gae.server.app.model.app.info.impl.AppServiceVersionInfoImpl;
import com.dereekb.gae.server.app.model.app.info.impl.AppVersionImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.google.appengine.api.utils.SystemProperty;
import com.google.appengine.api.utils.SystemProperty.Environment.Value;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;

public class GoogleAppEngineUtility {

	public static final String VERSION_JOINER = ".";
	public static final String VERSION_SPLITTER = "\\.";
	public static final String APP_ENGINE_APP_DOMAIN = "appspot.com";

	/**
	 * @see https://cloud.google.com/appengine/docs/standard/java/how-requests-are-routed
	 */
	public static final String APP_ENGINE_HTTPS_URL_DOT = "-dot-";

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
		// TODO: Consider using the recommended modules service instead,
		// described at
		// https://cloud.google.com/appengine/docs/standard/java/javadoc/com/google/appengine/api/modules/ModulesService#i7

		Environment environment = getApiEnvironment();

		String app = sanitizeAppId(environment.getAppId());
		String service = environment.getModuleId();
		String version = environment.getVersionId();

		return new AppServiceVersionInfoImpl(app, service, decodeAppVersion(version));
	}

	/**
	 * Google App Engine's environment will occasionally append a region prefix
	 * to appId values. This function will remove that prefix if present.
	 *
	 * @param appId
	 * @return
	 */
	public static String sanitizeAppId(String appId) {
		return appId.replaceFirst(".+~", "");
	}

	public static String getRawApplicationId() {
		return ApiProxy.getCurrentEnvironment().getAppId();
	}

	public static String getApplicationId() {
		return sanitizeAppId(ApiProxy.getCurrentEnvironment().getAppId());
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

	public static String urlForCurrentService() {
		return urlForCurrentService(false);
	}

	public static String urlForCurrentService(boolean includeVersion) {
		AppServiceVersionInfo appInfo = getApplicationInfo();
		return urlForService(appInfo.getAppProjectId(), appInfo.getAppService(),
		        (includeVersion) ? appInfo.getAppVersion().getMajorVersion() : null);
	}

	public static String urlForService(String appProjectId,
	                                   String appServiceName) {
		return urlForService(appProjectId, appServiceName, null);
	}

	public static String urlForService(String appProjectId,
	                                   String appServiceName,
	                                   String appMajorVersion) {

		// Ensure if a raw app ID is passed it is sanitized.
		appProjectId = sanitizeAppId(appProjectId);

		String subdomain = StringUtility.joinValues(APP_ENGINE_HTTPS_URL_DOT, appMajorVersion, appServiceName,
		        appProjectId);

		// https://[MY_PROJECT_ID].appspot.com
		// https://[SERVICE_ID]-dot-[MY_PROJECT_ID].appspot.com
		// https://[VERSION_ID]-dot-[SERVICE_ID]-dot-[MY_PROJECT_ID].appspot.com
		return "https://" + subdomain + "." + APP_ENGINE_APP_DOMAIN;
	}

}
