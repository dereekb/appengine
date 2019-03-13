package com.dereekb.gae.server.app.model.app.info;

/**
 * Information about the domain the app is a part of.
 * <p>
 * This info should be the same for each component
 *
 * @author dereekb
 *
 */
public interface AppDomainInfo {

	/**
	 * Returns the domain name the app is hosted on.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppDomain();

	/**
	 * Returns the url of the app.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getAppDomainUrl();

}
