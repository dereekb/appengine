package com.dereekb.gae.server.notification.service;

import java.util.Collection;

/**
 * Used for creating/adding/removing a token from a group.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface PushNotificationDeviceGroupUpdateRequest
        extends PushNotificationDeviceGroupRequest {

	/**
	 * The existing token, if applicable.
	 *
	 * @return {@link PushNotificationToken}, or {@code null}.
	 */
	public PushNotificationToken getToken();

	/**
	 * Returns the list of device tokens to modify in the group.
	 *
	 * @return {@link Collection}. Never {@code null}.
	 */
	public Collection<PushNotificationToken> getDeviceTokens();

}
