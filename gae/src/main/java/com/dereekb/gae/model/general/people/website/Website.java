package com.dereekb.gae.model.general.people.website;

/**
 * Represents a website with a service and url.
 *
 * @author dereekb
 *
 */
public interface Website {

	/**
	 * Returns the service for this {@link Website}.
	 *
	 * @return {@link String} for the service, or {@code null} if unavailable.
	 */
	public String getTitle();

	/**
	 * Returns the full url for this {@link Website}.
	 *
	 * @return {@link String} containing the full url. Never {@code null}.
	 */
	public String getUrl();

}
