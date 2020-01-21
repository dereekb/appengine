package com.dereekb.gae.server.notification.user.service;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Service for adding/removing devices to/from the push notification service for
 * a user.
 *
 * @author dereekb
 *
 */
public interface UserPushNotificationTokenService {

	/**
	 * Returns the token accessor for a user.
	 *
	 * @param userKey
	 * @return {@link UserPushNotificationTokenAccessor}. Never {@code null}.
	 */
	public UserPushNotificationTokenAccessor makeTokenAccessor(ModelKey userKey);

}
