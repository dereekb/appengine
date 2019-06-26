package com.dereekb.gae.server.app.model.app.info;


/**
 * {@link AppInfo} for a system {@link App}.
 *Ï
 * @author dereekb
 *
 */
public interface SystemAppInfo extends AppInfo {

	/**
	 * Returns the system key for the app, if specified.
	 *
	 * @return {@link String}, or {@code null} if not specified.
	 */
	public String getSystemKey();

}
