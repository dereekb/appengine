package com.dereekb.gae.model.general.people.website;

/**
 * Represents a website with a title and url.
 *
 * @author dereekb
 *
 */
public interface Website {

	/**
	 * Returns the title for this {@link Website}.
	 *
	 * @return {@link String} for the title, or {@code null} if unavailable.
	 */
	public String getWebsiteTitle();

	/**
	 * Returns the full url for this {@link Website}.
	 *
	 * @return {@link String} containing the full url. Never {@code null}.
	 */
	public String getWebsiteUrl();

	/**
	 * Encodes this model's data into a {@link WebsiteAddress}.
	 *
	 * @return {@link WebsiteAddress} for this model.
	 */
	public WebsiteAddress toWebsiteAddress();

}
