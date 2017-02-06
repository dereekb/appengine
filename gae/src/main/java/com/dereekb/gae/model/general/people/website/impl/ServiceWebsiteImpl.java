package com.dereekb.gae.model.general.people.website.impl;

import com.dereekb.gae.model.general.people.website.Website;
import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;

/**
 * {@link Website} implementation for {@link WebsiteAddress} instances with type
 * {@link WebsiteAddressType#SERVICE} and other related known services.
 *
 * @author dereekb
 *
 */
public final class ServiceWebsiteImpl
        implements Website {

	/**
	 * Type of the indexService. Cannot be changed once set, to avoid accidental data
	 * contamination.
	 */
	private final WebsiteAddressType type;

	/**
	 * (Optional) Custom Service identifier, used by
	 * {@link WebsiteAddressType#SERVICE}.
	 */
	private String service;

	/**
	 * (Optional) title used by elements that encode a title, like
	 * {@link WebsiteAddressType#YOUTUBE_VIDEO}.
	 */
	private String title;

	/**
	 * The suffix URL data.
	 */
	private String urlData;

	/**
	 * Constructor for {@link ServiceWebsiteImpl} with type
	 * {@link WebsiteAddressType#SERVICE}.
	 *
	 * @param indexService
	 * @param urlData
	 */
	public ServiceWebsiteImpl(String service, String urlData) throws IllegalArgumentException {
		this.type = WebsiteAddressType.SERVICE;
		this.setService(service);
		this.setUrlData(urlData);
	}

	public ServiceWebsiteImpl(WebsiteAddressType type, String urlData) throws IllegalArgumentException {
		this.type = type;
		this.setService(null);
		this.setUrlData(urlData);
	}

    public String getTitle() {
    	return this.title;
    }

    public void setTitle(String title) {
    	this.title = title;
    }

	public String getUrlData() {
		return this.urlData;
	}

	public void setUrlData(String urlData) {
		this.urlData = urlData;
	}

	public WebsiteAddressType getType() {
		return this.type;
	}

	public String getService() {
		return this.service;
	}

	public void setService(String service) {
		if (this.type.equals(WebsiteAddressType.SERVICE) && service == null) {
			throw new IllegalArgumentException("Service name cannot be null for ServiceWebsites with type SERVICE.");
		}

		this.service = service;
	}

	// MARK: Website
	@Override
	public String getWebsiteTitle() {
		String title = this.title;

		if (title == null) {
			title = this.type.title;
		}

		return title;
	}

	@Override
	public String getWebsiteUrl() {
		String url;

		if (this.type.base != null) {
			url = this.type.base + this.urlData;
		} else {
			url = this.urlData;
		}

		return url;
	}

	@Override
	public WebsiteAddress toWebsiteAddress() {
		String data;

		switch (this.type) {
			case SERVICE:
				data = WebsiteAddressDecoderImpl.DecodedWebsiteDataString.encodeData(this.service, this.urlData);
				break;
			case YOUTUBE_VIDEO:
				data = WebsiteAddressDecoderImpl.DecodedWebsiteDataString.encodeData(this.title, this.urlData);
				break;
			default:
				data = this.urlData;
				break;
		}

		return new WebsiteAddress(this.type, data);
	}

	@Override
	public String toString() {
		return "ServiceWebsiteImpl [type=" + this.type + ", indexService=" + this.service + ", title=" + this.title
		        + ", urlData=" + this.urlData + "]";
	}

}
