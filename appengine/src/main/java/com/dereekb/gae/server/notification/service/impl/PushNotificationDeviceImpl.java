package com.dereekb.gae.server.notification.service.impl;

import com.dereekb.gae.server.notification.service.PushNotificationDevice;

/**
 * {@link PushNotificationDevice} implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationDeviceImpl
        implements PushNotificationDevice {

	private String device;
	private String notificationToken;

	public PushNotificationDeviceImpl(String device, String notificationToken) {
		super();
		this.setDevice(device);
		this.setNotificationToken(notificationToken);
	}

	// MARK: PushNotificationDevice
	@Override
	public String getDevice() {
		return this.device;
	}

	public void setDevice(String device) {
		if (device == null) {
			throw new IllegalArgumentException("device cannot be null.");
		}

		this.device = device;
	}

	@Override
	public String getNotificationToken() {
		return this.notificationToken;
	}

	public void setNotificationToken(String notificationToken) {
		if (notificationToken == null) {
			throw new IllegalArgumentException("notificationToken cannot be null.");
		}

		this.notificationToken = notificationToken;
	}

	@Override
	public String toString() {
		return "PushNotificationDeviceImpl [device=" + this.device + ", notificationToken=" + this.notificationToken
		        + "]";
	}

}
