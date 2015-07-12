package com.dereekb.gae.model.general.people.website.impl;

import com.dereekb.gae.model.general.people.website.Website;
import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;
import com.dereekb.gae.model.general.people.website.impl.WebsiteAddressDecoderImpl.DecodedWebsiteDataString;

/**
 * Default {@link Website} implementation, that has a type of
 * {@link WebsiteAddressType#WEBSITE}.
 *
 * Has a url and an optional title.
 *
 * @author dereekb
 */
public class WebsiteImpl
        implements Website {

	public static final String DEFAULT_TITLE = WebsiteAddressType.WEBSITE.title;

	/**
	 * (Optional) website title
	 */
	private String title;

	/**
	 * The url, starting with http(s)://
	 */
	private String url;

	public WebsiteImpl(String title, String url) {
		this.title = title;
		this.url = url;
	}

	/**
	 * Constructor using a {@link WebsiteAddress} with type
	 * {@link WebsiteAddressType#WEBSITE}.
	 *
	 * @param address
	 * @throws IllegalArgumentException
	 *             if {@code address} is null or its {@link WebsiteAddressType}
	 *             is not {@link WebsiteAddressType#WEBSITE}.
	 */
	public WebsiteImpl(WebsiteAddress address) throws IllegalArgumentException {
		this.updateFromAddress(address);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) throws IllegalArgumentException {
		if (url == null) {
			throw new IllegalArgumentException("The url cannot be null.");
		}

		this.url = url;
	}

	public void updateFromAddress(WebsiteAddress address) throws IllegalArgumentException {
		if (address == null) {
			throw new IllegalArgumentException("The address cannot be null.");
		}

		if (address.getType().equals(WebsiteAddressType.WEBSITE)) {
			throw new IllegalArgumentException("The WebsiteAddress must have type WEBSITE.");
		}

		String addressData = address.getData();
		DecodedWebsiteDataString data = WebsiteAddressDecoderImpl.DecodedWebsiteDataString.decodeData(addressData);

		this.setTitle(data.getInfo());
		this.setUrl(data.getData());
	}

	// MARK: Website

	@Override
	public String getWebsiteTitle() {
		return this.title;
	}

	@Override
	public String getWebsiteUrl() {
		return this.url;
	}

	@Override
	public WebsiteAddress toWebsiteAddress() {
		String data = WebsiteAddressDecoderImpl.DecodedWebsiteDataString.encodeData(this.title, this.url);
		return new WebsiteAddress(WebsiteAddressType.WEBSITE, data);
	}

}
