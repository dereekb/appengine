package com.dereekb.gae.server.notification.service.impl.firebase;

import com.dereekb.gae.server.notification.service.PushNotificationToken;
import com.dereekb.gae.utilities.data.StringUtility;

/**
 * {@link PushNotificationToken} implementation.
 *
 * @author dereekb
 *
 */
public class PushNotificationTokenImpl
        implements PushNotificationToken {

	private String notificationToken;

	public PushNotificationTokenImpl(String notificationToken) {
		this.setNotificationToken(notificationToken);
	}

	// MARK: PushNotificationToken
	@Override
	public String getNotificationToken() {
		return this.notificationToken;
	}

	public void setNotificationToken(String notificationToken) {
		if (StringUtility.isEmptyString(notificationToken)) {
			throw new IllegalArgumentException("notificationToken cannot be null or empty.");
		}

		this.notificationToken = notificationToken;
	}

	@Override
	public String toString() {
		return "PushNotificationTokenImpl [notificationToken=" + this.notificationToken + "]";
	}

}
