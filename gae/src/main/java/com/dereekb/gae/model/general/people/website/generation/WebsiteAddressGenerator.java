package com.dereekb.gae.model.general.people.website.generation;

import com.dereekb.gae.model.extension.generation.impl.AbstractGenerator;
import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;
import com.dereekb.gae.model.general.people.website.impl.ServiceWebsiteImpl;
import com.dereekb.gae.model.general.people.website.impl.WebsiteImpl;
import com.dereekb.gae.utilities.misc.random.IntegerGenerator;

/**
 * {@link Generator} for {@link WebsiteAddress} instances.
 * 
 * @author dereekb
 *
 */
public class WebsiteAddressGenerator extends AbstractGenerator<WebsiteAddress> {

	private static final IntegerGenerator INTEGER_GENERATOR = new IntegerGenerator(0,
	        WebsiteAddressType.values().length);

	@Override
	public WebsiteAddress generate(Long seed) {
		Integer key = INTEGER_GENERATOR.generate(seed);
		WebsiteAddress address = null;

		WebsiteAddressType type = WebsiteAddressType.typeForId(key);

		switch (type) {
			case FACEBOOK:
			case GOOGLE:
			case INSTAGRAM:
			case PINTEREST:
			case TWITTER:
			case YOUTUBE_CHANNEL:
			case YOUTUBE_VIDEO:
				ServiceWebsiteImpl socialWebsite = new ServiceWebsiteImpl(type, "abcdefg");
				address = socialWebsite.toWebsiteAddress();
				break;
			case SERVICE:
				ServiceWebsiteImpl serviceWebsite = new ServiceWebsiteImpl("Flickr", "abcdefg");
				address = serviceWebsite.toWebsiteAddress();
				break;
			case WEBSITE:
				WebsiteImpl website = new WebsiteImpl("Google", "https://www.google.com");
				address = website.toWebsiteAddress();
				break;
			default:
				break;

		}

		return address;
	}

}
