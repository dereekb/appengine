package com.dereekb.gae.test.app.server.notification;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.service.PushNotificationDevice;
import com.dereekb.gae.server.notification.service.impl.PushNotificationDeviceImpl;
import com.dereekb.gae.server.notification.user.service.impl.NotificationSettingsTokenServiceImpl;
import com.dereekb.gae.server.notification.user.service.impl.NotificationSettingsTokenServiceImpl.NotificationSettingsTokenAccessorImpl;
import com.dereekb.gae.server.notification.user.service.impl.UserPushNotificationServiceImpl;
import com.dereekb.gae.test.app.mock.context.AbstractAppTestingContext;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link UserPushNotificationServiceImpl} and
 * {@link NotificationSettingsTokenServiceImpl} tests.
 *
 * @author dereekb
 *
 */
public class UserNoficationServiceTest extends AbstractAppTestingContext {

	private static final ModelKey TEST_MODEL_KEY = new ModelKey(1L);
	private static final String TEST_DEVICE_ID = "UUID";
	private static final String TEST_TOKEN = "TOKEN";

	@Autowired
	@Qualifier("userPushNotificationService")
	private UserPushNotificationServiceImpl userPushNotificationServiceImpl;

	private NotificationSettingsTokenServiceImpl notificationSettingsTokenServiceImpl;

	@BeforeEach
	public void setup() {
		this.notificationSettingsTokenServiceImpl = this.userPushNotificationServiceImpl
		        .getNotificationSettingsTokenService();
	}

	// MARK: NotificationSettingsTokenService Tests
	@Test
	public void testAddDevice() {
		NotificationSettingsTokenAccessorImpl accessor = this.notificationSettingsTokenServiceImpl
		        .makeTokenAccessor(TEST_MODEL_KEY);

		assertTrue(accessor.getUserPushNotificationTokens().isEmpty());

		PushNotificationDevice device = new PushNotificationDeviceImpl(TEST_DEVICE_ID, TEST_TOKEN);
		accessor.addDevice(device);

		assertFalse(accessor.getUserPushNotificationTokens().isEmpty());
	}

	@Test
	public void testRemoveDevice() {
		NotificationSettingsTokenAccessorImpl accessor = this.notificationSettingsTokenServiceImpl
		        .makeTokenAccessor(TEST_MODEL_KEY);

		PushNotificationDevice device = new PushNotificationDeviceImpl(TEST_DEVICE_ID, TEST_TOKEN);
		accessor.addDevice(device);

		assertFalse(accessor.getUserPushNotificationTokens().isEmpty());

		accessor.removeUserPushNotificationTokens(ListUtility.wrap(device));

		assertTrue(accessor.getUserPushNotificationTokens().isEmpty());
	}

}
