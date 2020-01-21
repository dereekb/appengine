package com.dereekb.gae.server.notification.service;

import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;

/**
 * Error that contains a token and the exception.
 *
 * @author dereekb
 *
 */
public interface PushNotificationSendResponseTokenError {

	/**
	 * Returns the token.
	 *
	 * @return {@link PushNotificationToken}. Never {@code null}.
	 */
	public PushNotificationToken getPushNotificationToken();

	/**
	 * Returns the send exception.
	 *
	 * @return {@link PushNotificationSendException}. Never {@code null}.
	 */
	public PushNotificationSendException getSendException();

}
