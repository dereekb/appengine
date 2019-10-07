package com.dereekb.gae.server.notification.service.impl.firebase;

import com.dereekb.gae.server.api.firebase.FirebaseService;
import com.dereekb.gae.server.notification.service.PushNotificationSendRequest;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponse;
import com.dereekb.gae.server.notification.service.PushNotificationService;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 *
 * @author dereekb
 *
 */
public class FirebasePushNotificationService
        implements PushNotificationService {

	private FirebaseService firebaseService;

	@Override
	public PushNotificationSendResponse sendNotification(PushNotificationSendRequest request) {
		// TODO.

		return null;
	}

	// MARK: Internal
	public FirebaseMessaging getMessagingInstance() {
		return FirebaseMessaging.getInstance(this.firebaseService.getFirebaseApp());
	}

}
