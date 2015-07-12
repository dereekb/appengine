package com.dereekb.gae.model.general.people.website.impl;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;

/**
 * Represents a {@link WebsiteAddress} that has been decoded. This type is
 * immutable.
 *
 * @author dereekb
 * @see {@link WebsiteImpl} for editing {@link WebsiteAddress} with website
 *      types.
 * @see {@link ServiceWebsiteImpl} for editing {@link WebsiteAddress} with
 *      service types.
 */
public class DecodedWebsiteAddress {

	/**
	 * Interpreted {@link WebsiteAddressType}.
	 */
	private final WebsiteAddressType type;

	/**
	 * (Optional) Title of the page.
	 *
	 * Used by {@link WebsiteAddressType#WEBSITE} types.
	 */
	private final String title;

	/**
	 * The name of the service. i.e. {@code Facebook}.
	 *
	 * Used by {@link WebsiteAddressType#SERVICE} types for specifying custom
	 * types.
	 */
	private final String service;

	/**
	 * the urlData
	 */
	private final String urlData;

	/**
	 * Default constructor for contructing {@link WebsiteAddressType#WEBSITE}
	 * types.
	 *
	 * @param title
	 * @param urlData
	 */
	public DecodedWebsiteAddress(String title, String urlData) {
		this.type = WebsiteAddressType.WEBSITE;
		this.service = null;
		this.title = title;
		this.urlData = urlData;
	}

	public DecodedWebsiteAddress(WebsiteAddressType type, String service, String title, String urlData) {
		this.type = type;
		this.service = service;
		this.title = title;
		this.urlData = urlData;
	}

	public WebsiteAddressType getType() {
		return this.type;
	}

	public String getTitle() {
		return this.title;
	}

	public String getService() {
		return this.service;
	}

	public String getUrlData() {
		return this.urlData;
	}

	public static DecodedWebsiteAddress withWebsite(String title,
	                                                String urlData) {
		return new DecodedWebsiteAddress(WebsiteAddressType.WEBSITE, null, title, urlData);
	}

	public static DecodedWebsiteAddress withService(String service,
	                                                String urlData) {
		return new DecodedWebsiteAddress(WebsiteAddressType.SERVICE, service, null, urlData);
	}

}
