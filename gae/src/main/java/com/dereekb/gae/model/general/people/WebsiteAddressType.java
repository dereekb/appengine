package com.dereekb.gae.model.general.people;

/**
 * Type information for {@link WebsiteAddress}.
 *
 * @author dereekb
 */
public enum WebsiteAddressType {

	/**
	 * A basic website.
	 *
	 * Data is formatted as a URL, or as a Title/URL combination that is
	 * separated by spaces.
	 *
	 * THIS IS A EXAMPLE http://www.example.com
	 */
	WEBSITE(0),

	/**
	 * A generic social media type for website types that are not available.
	 *
	 * Is the same as website, except may be handle differently by a parser.
	 *
	 * Data is formatted as a URL, or as a WebsiteAddress/URL combination that is
	 * separated by spaces.
	 *
	 * Facebook http://www.facebook.com
	 */
	MEDIA(1),

	/**
	 * Facebook Link.
	 *
	 * Data is formatted as the url relative to "www.facebook.com".
	 *
	 * Example: http://www.facebook.com/facebook -> "facebook"
	 */
	FACEBOOK(2),

	/**
	 * Twitter Link.
	 *
	 * Data is formatted as the userhandle.
	 *
	 * Example: \@example -> "example"
	 */
	TWITTER(3),

	/**
	 * Google Plus Link
	 */
	GOOGLE(4),

	/**
	 * Youtube Channel or Video Link
	 *
	 * Data is formatted as a link relative to Youtube.com
	 *
	 */
	YOUTUBE(5),

	/**
	 * Instagram Link
	 *
	 *
	 */
	INSTAGRAM(6);

	private final Integer type;

	private WebsiteAddressType(Integer type) {
		this.type = type;
	}

	public Integer getId() {
		return this.type;
	}

	public static WebsiteAddressType typeForId(Integer typeId) {
		WebsiteAddressType type;

		switch (typeId) {
			case 0:
				type = WEBSITE;
				break;
			case 1:
				type = MEDIA;
				break;
			case 2:
				type = FACEBOOK;
				break;
			case 3:
				type = TWITTER;
				break;
			case 4:
				type = GOOGLE;
				break;
			case 5:
				type = YOUTUBE;
				break;
			case 6:
				type = INSTAGRAM;
				break;
			default:
				type = WEBSITE;
				break;
		}

		return type;
	}

}
