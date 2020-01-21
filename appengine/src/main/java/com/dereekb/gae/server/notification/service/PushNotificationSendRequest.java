package com.dereekb.gae.server.notification.service;

/**
 * {@link PushNotificationService} send request.
 *
 * @author dereekb
 *
 */
public interface PushNotificationSendRequest
        extends PushNotificationSendRequestBody {

	/**
	 * Returns the token the notification is being sent to.
	 *
	 * @return {@link PushNotificationTokenSet}. Never {@code null}.
	 */
	public PushNotificationTokenSet getTokenSet();

}
