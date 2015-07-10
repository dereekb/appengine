package com.dereekb.gae.model.general.people.website.utility;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;

/**
 * Represents a decoded {@link WebsiteAddress}.
 *
 * @author dereekb
 * @see {@link WebsiteAddressReader} for building {@link InterpretedWebsite}
 *      values.
 */
public class InterpretedWebsite {

	/**
	 * Interpreted {@link WebsiteAddressType}.
	 */
	private WebsiteAddressType type;

	/**
	 * (Optional) Title of the page.
	 *
	 * Used by {@link WebsiteAddressType#WEBSITE} types.
	 */
	private String title;

	/**
	 * The name of the service. i.e. {@code Facebook}.
	 *
	 * Used by {@link WebsiteAddressType#SERVICE} types for specifying custom
	 * types.
	 */
	private String customService;

	/**
	 * The urlData used in URL construction.
	 *
	 * Used by all {@link WebsiteAddressType} types. Should not include service
	 * information.
	 */
	private String urlData;

	/**
	 * Default constructor for contructing {@link WebsiteAddressType#WEBSITE}
	 * types.
	 *
	 * @param title
	 * @param urlData
	 */
	public InterpretedWebsite(String title, String urlData) {
		this.type = WebsiteAddressType.WEBSITE;
		this.title = title;
		this.urlData = urlData;
	}

	/**
	 * Constructor generally used for known {@link WebsiteAddressType} social
	 * media types.
	 *
	 * @param type
	 * @param urlData
	 */
	public InterpretedWebsite(WebsiteAddressType type, String urlData) {
		this.type = type;
		this.urlData = urlData;
	}

	public InterpretedWebsite(WebsiteAddressType type, String customService, String title, String urlData) {
		this.type = type;
		this.customService = customService;
		this.title = title;
		this.urlData = urlData;
	}

	public static InterpretedWebsite withWebsite(String title,
	                                             String urlData) {
		return new InterpretedWebsite(WebsiteAddressType.WEBSITE, null, title, urlData);
	}

	public static InterpretedWebsite withService(String service,
	                                             String urlData) {
		return new InterpretedWebsite(WebsiteAddressType.WEBSITE, service, null, urlData);
	}

	public WebsiteAddressType getType() {
		return this.type;
	}

	public void setType(WebsiteAddressType type) {
		this.type = type;
	}

    public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomService() {
		return this.customService;
	}

	public void setCustomService(String customService) {
		this.customService = customService;
	}

	public String getUrlData() {
		return this.urlData;
	}

	public void setUrlData(String urlData) {
		this.urlData = urlData;
	}

}
