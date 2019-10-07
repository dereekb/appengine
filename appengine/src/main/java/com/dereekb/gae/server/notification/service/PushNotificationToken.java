package com.dereekb.gae.server.notification.service;

/**
 * Push Notification token associated with a specific device or group of
 * devices.
 *
 * @author dereekb
 *
 */
public interface PushNotificationToken {

	/**
	 * Returns the device/notification token.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getNotificationToken();

}
