package com.dereekb.gae.model.general.people.website.utility.impl;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;
import com.dereekb.gae.model.general.people.website.utility.InterpretedWebsite;
import com.dereekb.gae.model.general.people.website.utility.WebsiteInterpreter;

/**
 * {@link WebsiteIterpreter} that uses {@link DefaultServiceInterpreter} and
 * {@link KnownServiceInterpreter} static functions.
 *
 * @author dereekb
 * @see {@link WebsiteInterpreterMap} for similar implementation.
 */
public class WebsiteInterpreterImpl
        implements WebsiteInterpreter {

	@Override
	public WebsiteAddress convertToAddress(InterpretedWebsite website) {
		WebsiteAddressType type = website.getType();
		WebsiteAddress address = null;

		switch (type) {
			case FACEBOOK:
			case GOOGLE:
			case INSTAGRAM:
			case PINTEREST:
			case TWITTER:
			case YOUTUBE_CHANNEL:
			case YOUTUBE_VIDEO:
				address = KnownServiceInterpreter.convert(website);
				break;
			case SERVICE:
			case WEBSITE:
				address = DefaultServiceInterpreter.convert(website);
				break;
			default:
				throw new RuntimeException("Unknown address type.");
		}

		return address;
	}

	@Override
	public InterpretedWebsite convertFromAddress(WebsiteAddress address) {
		WebsiteAddressType type = address.getType();
		InterpretedWebsite website = null;

		switch (type) {
			case FACEBOOK:
			case GOOGLE:
			case INSTAGRAM:
			case PINTEREST:
			case TWITTER:
			case YOUTUBE_CHANNEL:
			case YOUTUBE_VIDEO:
				website = KnownServiceInterpreter.convert(address);
				break;
			case SERVICE:
			case WEBSITE:
				website = DefaultServiceInterpreter.convert(address);
				break;
			default:
				throw new RuntimeException("Unknown address type.");
		}

		return website;
	}

}
