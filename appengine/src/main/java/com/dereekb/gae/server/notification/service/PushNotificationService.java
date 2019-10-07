package com.dereekb.gae.server.notification.service;

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
	 */
	public PushNotificationSendResponse sendNotification(PushNotificationSendRequest request);




}
