package com.dereekb.gae.server.notification.service;

/**
 * Device group request notification request.
 *
 * @author dereekb
 * @see PushNotificationDeviceGroupUpdateRequest
 */
@Deprecated
public interface PushNotificationDeviceGroupRequest {

	/**
	 * Unique identifier for the device group.
	 * <p>
	 * Is typically a user's identifier.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getGroupKey();

}
