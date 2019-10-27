package com.dereekb.gae.server.notification.user.service;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.service.PushNotificationSendRequestBody;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponse;
import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;

/**
 * Service for sending push notifications to a single user.
 *
 * @author dereekb
 *
 */
public interface UserPushNotificationSendService {

	/**
	 * Sends a push notification.
	 *
	 * @param userKey
	 *            {@link ModelKey}. Never {@code null}.
	 * @param request
	 *            {@link PushNotificationSendRequestBody}. Never {@code null}.
	 * @return {@link PushNotificationSendResponse}. Never {@code null}.
	 * @throws PushNotificationSendException
	 *             thrown if there is an issue sending the request.
	 */
	public PushNotificationSendResponse send(ModelKey userKey,
	                                         PushNotificationSendRequestBody request)
	        throws PushNotificationSendException;

}
