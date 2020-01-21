package com.dereekb.gae.server.notification.service;

import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;

/**
 * Low-level service for sending push notifications and managing devices.
 *
 * @author dereekb
 *
 */
public interface PushNotificationService {

	/**
	 * Notification send request.
	 *
	 * @param request
	 *            {@link PushNotificationSendRequest}. Never {@code null}.
	 * @return {@link PushNotificationSendResponse}. Never {@code null}.
	 * @throws PushNotificationSendException
	 *             thrown when the send fails
	 */
	public PushNotificationSendResponse sendNotification(PushNotificationSendRequest request)
	        throws PushNotificationSendException;

}
