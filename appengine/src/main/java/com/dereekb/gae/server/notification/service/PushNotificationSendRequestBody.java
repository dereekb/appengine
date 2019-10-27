package com.dereekb.gae.server.notification.service;

import com.dereekb.gae.utilities.misc.parameters.Parameters;

/**
 * {@link PushNotificationService} send request body. Does not specify any
 * particular user/tokens.
 *
 * @author dereekb
 * @see PushNotificationSendRequest
 */
public interface PushNotificationSendRequestBody {

	/**
	 * Returns the title.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getTitle();

	/**
	 * Returns the message.
	 *
	 * @return {@link String} or {@code null}.
	 */
	public String getMessage();

	/**
	 * Returns an image url.
	 *
	 * @return {@link String} or {@code null}.
	 */
	public String getImageUrl();

	/**
	 * Returns the data associated with the request.
	 *
	 * @return {@link Parameters} or {@code null}.
	 */
	public Parameters getData();

	/**
	 * Whether or not there is info that should be processed immediately.
	 * <p>
	 * Used for priority requests.
	 *
	 * @return {@code true} if content is available.
	 */
	public boolean isContentAvailable();

}
