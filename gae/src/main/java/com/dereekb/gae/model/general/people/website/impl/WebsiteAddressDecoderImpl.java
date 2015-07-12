package com.dereekb.gae.model.general.people.website.impl;

import com.dereekb.gae.model.general.people.website.WebsiteAddress;
import com.dereekb.gae.model.general.people.website.WebsiteAddressDecoder;
import com.dereekb.gae.model.general.people.website.WebsiteAddressType;

/**
 * {@link WebsiteAddressDecoder} implementation.
 *
 * @author dereekb
 *
 */
public class WebsiteAddressDecoderImpl
        implements WebsiteAddressDecoder {

	@Override
	public DecodedWebsiteAddress decodeAddress(WebsiteAddress address) {
		WebsiteAddressType type = address.getType();
		String addressData = address.getData();

		String title = type.getTitle();
		String service = type.getService();
		String urlData = null;

		boolean decode = false;
		boolean decodeService = false;

		switch (type) {
			case FACEBOOK:
			case GOOGLE:
			case INSTAGRAM:
			case PINTEREST:
			case TWITTER:
			case YOUTUBE_CHANNEL:
				urlData = addressData;
				break;
			case SERVICE:
				decodeService = true;
			case YOUTUBE_VIDEO: // Models with title/url data mixed.
			case WEBSITE:
				decode = true;
				break;
		}

		if (decode) {
			DecodedWebsiteDataString decoded = DecodedWebsiteDataString.decodeData(addressData);
			urlData = decoded.getData();

			if (decodeService) {
				service = decoded.getInfo();
			} else {
				title = decoded.getInfo();
			}
		}

		return new DecodedWebsiteAddress(type, service, title, urlData);
	}

	public static class DecodedWebsiteDataString {

		private static final String ENCODED_DATA_STRING_FORMAT = "%s %s";

		/**
		 * Represents the optional info before the primary URL data. Generally a
		 * title, or service name.
		 */
		public final String info;

		/**
		 * The primary URL data.
		 */
		public final String data;

		public DecodedWebsiteDataString(String info, String data) {
			this.info = info;
			this.data = data;
		}

		public String getInfo() {
			return this.info;
		}

		public String getData() {
			return this.data;
		}

		public static DecodedWebsiteDataString decodeData(String addressData) {
			addressData = addressData.trim();
			int split = addressData.lastIndexOf(" ");

			String info;
			String data;

			if (split != -1) {
				info = addressData.substring(0, split);
				data = addressData.substring(split + 1);
			} else {
				info = null;
				data = addressData;
			}

			return new DecodedWebsiteDataString(info, data);
		}

		public static String encodeData(String info,
		                                String data) {
			String encoded = data;

			if (info != null) {
				data = String.format(ENCODED_DATA_STRING_FORMAT, info.trim(), data);
			}

			return encoded;
		}

	}

}
