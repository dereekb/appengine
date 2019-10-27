package com.dereekb.gae.server.notification.user.service;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.service.PushNotificationToken;

/**
 * Used for managing tokens for a user.
 *
 * @author dereekb
 *
 */
public interface UserPushNotificationTokenAccessor {

	/**
	 * Returns the user key this accessor is associated with.
	 *
	 * @return {@link ModelKey}. Never {@code null}.
	 */
	public ModelKey getUserKey();

	/**
	 * Returns all push notification tokens for a user.
	 *
	 * @param userKey
	 *            {@link ModelKey}. Never {@code null}.
	 * @return {@link List}. Never {@code null}.
	 */
	public List<PushNotificationToken> getUserPushNotificationTokens();

	/**
	 * Attempts to remove all specified push notification tokens from a user.
	 *
	 * @param {@link
	 *            List}. Never {@code null}.
	 * @return {@code true} if removal was successful.
	 */
	public boolean removeUserPushNotificationTokens(List<PushNotificationToken> token);

}
