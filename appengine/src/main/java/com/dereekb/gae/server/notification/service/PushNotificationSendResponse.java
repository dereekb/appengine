package com.dereekb.gae.server.notification.service;

import java.util.List;

/**
 * {@link PushNotificationService} send response.
 *
 * @author dereekb
 *
 */
public interface PushNotificationSendResponse {

	/**
	 * Returns the list of successful tokens.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<PushNotificationToken> getSuccessfulTokens();

	/**
	 * Returns the list of failed tokens.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<PushNotificationSendResponseTokenError> getFailedTokens();

}
