package com.dereekb.gae.model.general.people.website;

/**
 * Type information for {@link WebsiteAddress}.
 *
 * @author dereekb
 */
public enum WebsiteAddressType {

	/**
	 * A basic website.
	 *
	 * <p>
	 * Data is formatted as a URL, or as a Title/URL combination that is
	 * separated by spaces.
	 *
	 * <span> Example:
	 *
	 * <p>
	 * {@code THIS IS A EXAMPLE TITLE http://www.example.com}
	 */
	WEBSITE(0, "Website", null, null),

	/**
	 * <p>
	 * A generic indexService id for website types that are not officially supported
	 * by the server.
	 * <p>
	 * Is the same as website, except may be handle differently by a parser.<br>
	 * <p>
	 * Data is formatted as a URL, or as a WebsiteAddress/URL combination that
	 * is separated by spaces. The "indexService" component is for the indexService
	 * identifier.
	 *
	 * <p>
	 * {@code Facebook http://www.facebook.com}
	 */
	SERVICE(1, "Service", null, null),

	/**
	 * Facebook Link.
	 *
	 * <p>
	 * Data is formatted as the url relative to "www.facebook.com".
	 *
	 * <p>
	 * Example: http://www.facebook.com/facebook -> "facebook"
	 *
	 * <p>
	 *
	 * @see <a href="https://www.facebook.com">Facebook</a>
	 */
	FACEBOOK(2, "Facebook", "Facebook", "https://www.facebook.com"),

	/**
	 * Twitter Link
	 * <p>
	 * Data is formatted as a the username for Twitter..
	 *
	 * <p>
	 * Example: {@literal @example} -> {@code https://twitter.com/example}
	 *
	 * <p>
	 *
	 * @see <a href="https://twitter.com">Twitter</a>
	 */
	TWITTER(3, "Twitter", "Twitter", "https://twitter.com/"),

	/**
	 * Google Plus Link
	 *
	 * @see <a href="https://plus.google.com">Google Plus</a>
	 */
	GOOGLE(4, "Google Plus", "Google Plus", "https://plus.google.com/"),

	/**
	 * Youtube Video Link
	 * <p>
	 * Data is formatted as an optional title, and a link relative to
	 * {@code youtu.be} and {@code https://www.youtube.com/watch?v=}.
	 *
	 * <p>
	 * Example: {@code Video Title abcdef} {@literal ->}
	 * {@code https://www.youtube.com/watch?v=abcdefg} with title
	 * {@code Video Title}
	 *
	 * @see <a href="http://www.youtube.com">Youtube</a>
	 */
	YOUTUBE_VIDEO(5, "Youtube Video", "Youtube", "https://www.youtube.com/watch?v="),

	/**
	 * Youtube Channel Link
	 *
	 * <p>
	 * Data is formatted as a link relative to
	 * {@code https://www.youtube.com/channel}.
	 *
	 * <p>
	 * Example: {@code abcdef} ->
	 * {@code https://www.youtube.com/channel/abcdefg}
	 *
	 * @see <a href="https://www.youtube.com">Youtube</a>
	 */
	YOUTUBE_CHANNEL(6, "Youtube Channel", "Youtube", "https://www.youtube.com/channel/"),

	/**
	 * Instagram Link
	 *
	 * <p>
	 * Data is formatted as a link relative to
	 * {@code https://www.instagram.com/}.
	 *
	 * @see <a href="https://www.instagram.com">Instagram</a>
	 */
	INSTAGRAM(7, "Instagram", "Instagram", "https://www.instagram.com/"),

	/**
	 * Pinterest Link
	 *
	 * <p>
	 * Data is formatted as a link relative to
	 * {@code https://www.pinterest.com/}.
	 *
	 * <p>
	 * Example: {@code abcdefg} -> {@code https://www.pinterest.com/abcdefg}
	 *
	 * @see <a href="https://www.pinterest.com">Pinterest</a>
	 */
	PINTEREST(8, "Pinterest", "Pinterest", "https://www.pinterest.com/");

	public final Integer id;
	public final String title;
	public final String service;
	public final String base;

	private WebsiteAddressType(Integer type, String title, String service, String base) {
		this.id = type;
		this.title = title;
		this.service = service;
		this.base = base;
	}

	public Integer getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

    public String getService() {
		return this.service;
	}

    public String getBase() {
		return this.base;
	}

	public static WebsiteAddressType typeForId(Integer id) {
		WebsiteAddressType type;

		switch (id) {
			case 0:
				type = WEBSITE;
				break;
			case 1:
				type = SERVICE;
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
				type = YOUTUBE_VIDEO;
				break;
			case 6:
				type = YOUTUBE_CHANNEL;
				break;
			case 7:
				type = INSTAGRAM;
				break;
			case 8:
				type = PINTEREST;
				break;
			default:
				type = WEBSITE;
				break;
		}

		return type;
	}

}
