package com.dereekb.gae.model.general.people.website.utility.impl;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;
import com.dereekb.gae.model.general.people.website.exception.UnknownSocialMediaException;
import com.dereekb.gae.model.general.people.website.utility.InterpretedWebsite;
import com.dereekb.gae.model.general.people.website.utility.WebsiteInterpreter;

/**
 * Interpreter for {@link WebsiteAddressType#WEBSITE} and
 * {@link WebsiteAddressType#SERVICE} types.
 *
 * @author dereekb
 *
 */
public class DefaultServiceInterpreter
        implements WebsiteInterpreter {

	/**
	 * Simple regex to check for no spaces in a URL.
	 */
	private static final String URL_REGEX = "\\S+";
	private static final String TITLE_URL_MIX_FORMAT = "%s %s";

	@Override
	public WebsiteAddress convertToAddress(InterpretedWebsite website) throws UnknownSocialMediaException {
		return convert(website);
	}

	@Override
	public InterpretedWebsite convertFromAddress(WebsiteAddress address) {
		return convert(address);
	}

	public static WebsiteAddress convert(InterpretedWebsite website) throws UnknownSocialMediaException {
		WebsiteAddressType type = website.getType();
		String urlData = website.getUrlData();
		String data = null;

		switch (type) {
			case SERVICE:
				String service = website.getCustomService();
				data = dataForWebsiteData(service, urlData);
				break;
			case WEBSITE:
				String title = website.getTitle();
				data = dataForWebsiteData(title, urlData);
				break;
			default:
				throw new IllegalArgumentException("Only converts type of SERVICE and WEBSITE.");
		}

		return new WebsiteAddress(type, data);
	}

	private static String dataForWebsiteData(String front,
	                                         String url) throws IllegalArgumentException {
		String data = null;

		if (url == null || url.isEmpty()) {
			throw new IllegalArgumentException("URL cannot be null or empty.");
		}

		if (url.matches(URL_REGEX) == false) {
			throw new IllegalArgumentException("URL cannot contain spaces.");
        }

		data = url;

		if (front != null) {
			data = String.format(TITLE_URL_MIX_FORMAT, front.trim(), url);
		}

		return data;
	}

	public static InterpretedWebsite convert(WebsiteAddress address) {
		WebsiteAddressType type = address.getType();
		String addressData = address.getData();

		// Decode the title/service
		int split = addressData.lastIndexOf(" ");

		String title;
		String urlData;

		if (split != -1) {
			title = addressData.substring(0, split);
			urlData = addressData.substring(split);
		} else {
			title = null;
			urlData = addressData;
		}

		InterpretedWebsite website;

		switch (type) {
			case SERVICE:
				website = InterpretedWebsite.withService(title, urlData);
				break;
			case WEBSITE:
				website = InterpretedWebsite.withWebsite(title, urlData);
				break;
			default:
				throw new IllegalArgumentException("Only converts type of SERVICE and WEBSITE.");

		}

		return website;
	}

}
