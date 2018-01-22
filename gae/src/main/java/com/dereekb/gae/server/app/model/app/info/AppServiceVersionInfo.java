package com.dereekb.gae.server.app.model.app.info;

/**
 * Version information about a app service.
 *
 * @author dereekb
 *
 */
public interface AppServiceVersionInfo {

	public String getAppId();

	public AppVersion getAppVersion();

	public String getAppService();

}
