package com.dereekb.gae.server.notification.user.service;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.service.PushNotificationDevice;

/**
 * User's device registered with the push notification service.
 *
 * @author dereekb
 * @deprecated Use {@link PushNotificationDevice} instead. No use case aside
 *             from Add for use of this type.
 */
@Deprecated
public interface UserPushNotificationDevice
        extends PushNotificationDevice {

	/**
	 * Returns the device UUID or other identifier.
	 *
	 * @return {@link String}. Never {@code null}.
	 */
	public ModelKey getUserKey();

}
