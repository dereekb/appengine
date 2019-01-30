package com.dereekb.gae.model.general.people.website;

import com.dereekb.gae.model.general.people.website.impl.DecodedWebsiteAddress;

/**
 * Used for decoding {@link WebsiteAddress} to a {@link DecodedWebsiteAddress}.
 *
 * @author dereekb
 */
public interface WebsiteAddressDecoder {

	/**
	 * Decodes the input {@link WebsiteAddress}.
	 *
	 * @param address
	 * @return {@link DecodedWebsiteAddress} from {@code address} data.
	 */
	public DecodedWebsiteAddress decodeAddress(WebsiteAddress address);

}
