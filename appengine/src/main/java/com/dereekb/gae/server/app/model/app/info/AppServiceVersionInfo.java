package com.dereekb.gae.server.app.model.app.info;

/**
 * Version information about a app service.
 *
 * @author dereekb
 *
 */
public interface AppServiceVersionInfo {

	public String getAppProjectId();

	public AppVersion getAppVersion();

	public String getAppService();

}
