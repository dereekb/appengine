package com.dereekb.gae.server.notification.service.impl.firebase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.dereekb.gae.server.api.firebase.FirebaseService;
import com.dereekb.gae.server.notification.service.PushNotificationSendRequest;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponse;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponseTokenError;
import com.dereekb.gae.server.notification.service.PushNotificationService;
import com.dereekb.gae.server.notification.service.PushNotificationToken;
import com.dereekb.gae.server.notification.service.PushNotificationTokenSet;
import com.dereekb.gae.server.notification.service.PushNotificationTokenUtility;
import com.dereekb.gae.server.notification.service.exception.InvalidTokenPushNotificationSendException;
import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;
import com.dereekb.gae.server.notification.service.exception.SystemConfigurationPushNotificationSendException;
import com.dereekb.gae.server.notification.service.exception.SystemUnavailablePushNotificationSendException;
import com.dereekb.gae.server.notification.service.impl.PushNotificationSendResponseImpl;
import com.dereekb.gae.server.notification.service.impl.PushNotificationSendResponseTokenErrorImpl;
import com.dereekb.gae.utilities.data.StringUtility;
import com.dereekb.gae.utilities.misc.parameters.utility.ParameterUtility;
import com.google.firebase.messaging.ApnsConfig;
import com.google.firebase.messaging.Aps;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.SendResponse;

/**
 * {@link PushNotificationService} implementation using {@link FirebaseService}.
 *
 * @author dereekb
 *
 */
public class FirebasePushNotificationServiceImpl
        implements PushNotificationService {

	private static final String ANDROID_COLLAPSE_ID_KEY = "collapseKey";
	private static final String APNS_COLLAPSE_ID_KEY = "apns-collapse-id";

	private FirebaseService firebaseService;

	public FirebasePushNotificationServiceImpl(FirebaseService firebaseService) {
		this.setFirebaseService(firebaseService);
	}

	public FirebaseService getFirebaseService() {
		return this.firebaseService;
	}

	public void setFirebaseService(FirebaseService firebaseService) {
		if (firebaseService == null) {
			throw new IllegalArgumentException("firebaseService cannot be null.");
		}

		this.firebaseService = firebaseService;
	}

	// MARK: PushNotificationService
	@Override
	public PushNotificationSendResponse sendNotification(PushNotificationSendRequest request)
	        throws PushNotificationSendException {
		MulticastMessage message = this.buildMessage(request);
		BatchResponse batchResponse = null;

		try {
			batchResponse = this.getMessagingInstance().sendMulticast(message);
		} catch (FirebaseMessagingException e) {
			throw new PushNotificationSendException(e);
		}

		PushNotificationSendResponse response = this.buildResponse(request, batchResponse);
		return response;
	}

	private MulticastMessage buildMessage(PushNotificationSendRequest request) {

		PushNotificationTokenSet tokenSet = request.getTokenSet();
		List<PushNotificationToken> tokens = tokenSet.getTokens();
		List<String> tokenStrings = PushNotificationTokenUtility.readTokenStrings(tokens);

		boolean contentAvailable = request.isContentAvailable();

		String title = request.getTitle();
		String body = request.getMessage();
		String image = request.getImageUrl();

		Notification notification = Notification.builder().setTitle(title).setBody(body).setImage(image).build();

		String code = request.getCode();
		Map<String, String> data = ParameterUtility.safeMap(request.getData());

		if (code != null) {
			data.put("code", code);
		}

		String collapsesKey = null;	// TODO: Add collapse key usage later.

		Aps aps = Aps.builder().setContentAvailable(contentAvailable).build();
		ApnsConfig.Builder apnsConfigBuilder = ApnsConfig.builder().setAps(aps);

		// TODO: Add android-specific config?

		if (!StringUtility.isEmptyString(collapsesKey)) {
			apnsConfigBuilder.putHeader(APNS_COLLAPSE_ID_KEY, collapsesKey);
		}

		ApnsConfig apnsConfig = apnsConfigBuilder.build();

		return MulticastMessage.builder().addAllTokens(tokenStrings).setNotification(notification)
		        .setApnsConfig(apnsConfig).putAllData(data).build();
	}

	private PushNotificationSendResponse buildResponse(PushNotificationSendRequest request,
	                                                   BatchResponse batchResponse) {

		List<PushNotificationToken> successfulTokens = new ArrayList<PushNotificationToken>();
		List<PushNotificationSendResponseTokenError> failedTokens = new ArrayList<PushNotificationSendResponseTokenError>();

		List<PushNotificationToken> allTokens = request.getTokenSet().getTokens();

		List<SendResponse> responses = batchResponse.getResponses();

		for (int i = 0; i < responses.size(); i += 1) {
			PushNotificationToken token = allTokens.get(i);
			SendResponse response = responses.get(i);

			if (response.isSuccessful()) {
				successfulTokens.add(token);
			} else {
				FirebaseMessagingException exception = response.getException();
				PushNotificationSendException sendException = this.convertToSendException(exception);

				PushNotificationSendResponseTokenErrorImpl tokenError = new PushNotificationSendResponseTokenErrorImpl(
				        token, sendException);
				failedTokens.add(tokenError);
			}
		}

		PushNotificationSendResponse response = new PushNotificationSendResponseImpl(successfulTokens, failedTokens);
		return response;
	}

	private PushNotificationSendException convertToSendException(FirebaseMessagingException exception) {
		PushNotificationSendException sendException = null;

		String errorCode = exception.getErrorCode();

		// https://firebase.google.com/docs/cloud-messaging/send-message
		switch (errorCode) {
			case "messaging/invalid-registration-token":
			case "messaging/registration-token-not-registered":
				sendException = new InvalidTokenPushNotificationSendException(exception);
				break;
			case "messaging/invalid-apns-credentials":
			case "messaging/mismatched-credential":
			case "messaging/authentication-error":
				sendException = new SystemConfigurationPushNotificationSendException(exception);
				break;
			case "messaging/server-unavailable":
			case "messaging/internal-error":
			case "messaging/unknown-error":
				sendException = new SystemUnavailablePushNotificationSendException(exception);
				break;
			default:
				sendException = new PushNotificationSendException(exception);
				break;
		}

		return sendException;
	}

	// MARK: Internal
	public FirebaseMessaging getMessagingInstance() {
		return FirebaseMessaging.getInstance(this.firebaseService.getFirebaseApp());
	}

	@Override
	public String toString() {
		return "FirebasePushNotificationService [firebaseService=" + this.firebaseService + "]";
	}

}
