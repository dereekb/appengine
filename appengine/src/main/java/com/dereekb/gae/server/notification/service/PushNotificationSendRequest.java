package com.dereekb.gae.server.notification.service;

/**
 * {@link PushNotificationService} send request.
 *
 * @author dereekb
 *
 */
public interface PushNotificationSendRequest {

	/**
	 * Returns the token the notification is being sent to.
	 *
	 * @return {@link PushNotificationToken}. Never {@code null}.
	 */
	public PushNotificationToken getToken();

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
	 * Returns the data associated with the request.
	 *
	 * @return {@link Object} or {@code null}.
	 */
	public Object getData();

}
