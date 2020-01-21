package com.dereekb.gae.server.notification.user.service;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.service.PushNotificationDevice;

/**
 * Service for adding/removing devices to/from the push notification service for
 * a user.
 *
 * @author dereekb
 *
 */
public interface UserPushNotificationDeviceService {

	/**
	 * Adds a device to the user.
	 *
	 * @param userKey
	 * @param device
	 *            {@link UserPushNotificationDevice}. Never {@code null}.
	 */
	public void addDevice(ModelKey userKey,
	                      PushNotificationDevice device);

	/**
	 * Removes a device from the user.
	 *
	 * @param deviceId
	 *            {@link String}. Never {@code null} or empty.
	 */
	/*
	public void removeDevice(ModelKey user,
	                         String deviceId);
	*/

}
