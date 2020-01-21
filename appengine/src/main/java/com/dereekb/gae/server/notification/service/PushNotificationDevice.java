package com.dereekb.gae.server.notification.service;

/**
 * {@link PushNotificationToken} associated with a device/UUID.
 *
 * @author dereekb
 *
 */
public interface PushNotificationDevice
        extends PushNotificationToken {

	/**
	 * Returns the device UUID or other identifier.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public String getDevice();

	/**
	 * Returns the device's notification token.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	@Override
	public String getNotificationToken();

}
