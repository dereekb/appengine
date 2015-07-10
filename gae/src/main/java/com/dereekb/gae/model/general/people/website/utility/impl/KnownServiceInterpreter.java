package com.dereekb.gae.model.general.people.website.utility.impl;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;
import com.dereekb.gae.model.general.people.website.exception.UnknownSocialMediaException;
import com.dereekb.gae.model.general.people.website.utility.InterpretedWebsite;
import com.dereekb.gae.model.general.people.website.utility.WebsiteInterpreter;

/**
 * Default {@link WebsiteInterpreter} implementation for interpreting known
 * social media/service sites.
 *
 * @author dereekb
 *
 */
public class KnownServiceInterpreter
        implements WebsiteInterpreter {

	/**
	 * Static {@link KnownServiceInterpreter} singleton.
	 */
	public static final KnownServiceInterpreter INTERPRETER = new KnownServiceInterpreter();
	public static final WebsiteAddressType[] KNOWN_SERVICES = { WebsiteAddressType.FACEBOOK, WebsiteAddressType.GOOGLE,
	        WebsiteAddressType.INSTAGRAM, WebsiteAddressType.TWITTER, WebsiteAddressType.PINTEREST,
	        WebsiteAddressType.YOUTUBE_CHANNEL, WebsiteAddressType.YOUTUBE_VIDEO };

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
		String data = null;

		switch (type) {
			case FACEBOOK:
			case GOOGLE:
			case INSTAGRAM:
			case PINTEREST:
			case TWITTER:
			case YOUTUBE_CHANNEL:
			case YOUTUBE_VIDEO:
				data = website.getUrlData();
				break;
			default:
				throw new UnknownSocialMediaException(website);
		}

		return new WebsiteAddress(type, data);
	}

	public static InterpretedWebsite convert(WebsiteAddress address) {
		WebsiteAddressType type = address.getType();
		String data = address.getData();
		return new InterpretedWebsite(type, data);
	}

}
