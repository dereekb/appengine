package com.dereekb.gae.server.notification.user.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.notification.service.PushNotificationDevice;
import com.dereekb.gae.server.notification.service.PushNotificationSendRequest;
import com.dereekb.gae.server.notification.service.PushNotificationSendRequestBody;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponse;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponseTokenError;
import com.dereekb.gae.server.notification.service.PushNotificationService;
import com.dereekb.gae.server.notification.service.PushNotificationToken;
import com.dereekb.gae.server.notification.service.PushNotificationTokenSet;
import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;
import com.dereekb.gae.server.notification.service.exception.PushNotificationSendExceptionType;
import com.dereekb.gae.server.notification.service.impl.PushNotificationSendRequestImpl;
import com.dereekb.gae.server.notification.service.impl.PushNotificationSendResponseImpl;
import com.dereekb.gae.server.notification.service.impl.PushNotificationTokenSetImpl;
import com.dereekb.gae.server.notification.user.service.UserPushNotificationService;
import com.dereekb.gae.server.notification.user.service.impl.NotificationSettingsTokenServiceImpl.NotificationSettingsTokenAccessorImpl;

/**
 * {@link UserPushNotificationService} implementation that uses a
 * {@link NotificationSettingsTokenServiceImpl}.
 *
 * @author dereekb
 *
 */
public class UserPushNotificationServiceImpl
        implements UserPushNotificationService {

	private PushNotificationService pushNotificationService;
	private NotificationSettingsTokenServiceImpl notificationSettingsTokenService;

	public UserPushNotificationServiceImpl(PushNotificationService pushNotificationService,
	        NotificationSettingsTokenServiceImpl notificationSettingsTokenService) {
		super();
		this.setPushNotificationService(pushNotificationService);
		this.setNotificationSettingsTokenService(notificationSettingsTokenService);
	}

	public PushNotificationService getPushNotificationService() {
		return this.pushNotificationService;
	}

	public void setPushNotificationService(PushNotificationService pushNotificationService) {
		if (pushNotificationService == null) {
			throw new IllegalArgumentException("pushNotificationService cannot be null.");
		}

		this.pushNotificationService = pushNotificationService;
	}

	public NotificationSettingsTokenServiceImpl getNotificationSettingsTokenService() {
		return this.notificationSettingsTokenService;
	}

	public void setNotificationSettingsTokenService(NotificationSettingsTokenServiceImpl notificationSettingsTokenService) {
		if (notificationSettingsTokenService == null) {
			throw new IllegalArgumentException("notificationSettingsTokenService cannot be null.");
		}

		this.notificationSettingsTokenService = notificationSettingsTokenService;
	}

	// MARK: UserPushNotificationService
	@Override
	public void addDevice(ModelKey userKey,
	                      PushNotificationDevice device) {
		this.notificationSettingsTokenService.makeTokenAccessor(userKey).addDevice(device);
	}

	@Override
	public PushNotificationSendResponse send(ModelKey userKey,
	                                         PushNotificationSendRequestBody request)
	        throws PushNotificationSendException {

		NotificationSettingsTokenAccessorImpl accessor = this.notificationSettingsTokenService
		        .makeTokenAccessor(userKey);

		List<PushNotificationToken> tokens = accessor.getUserPushNotificationTokens();

		if (tokens.isEmpty()) {
			return PushNotificationSendResponseImpl.empty();
		}

		PushNotificationTokenSet tokenSet = new PushNotificationTokenSetImpl(tokens);
		PushNotificationSendRequest pushNotificationSendRequest = new PushNotificationSendRequestImpl(tokenSet,
		        request);

		PushNotificationSendResponse response = this.pushNotificationService
		        .sendNotification(pushNotificationSendRequest);

		List<PushNotificationSendResponseTokenError> failedTokens = response.getFailedTokens();
		List<PushNotificationToken> tokensToRemove = new ArrayList<PushNotificationToken>();

		for (PushNotificationSendResponseTokenError sendError : failedTokens) {
			PushNotificationSendException sendException = sendError.getSendException();

			if (sendException.getSendExceptionType() == PushNotificationSendExceptionType.INVALID_TOKEN_EXCEPTION) {
				tokensToRemove.add(sendError.getPushNotificationToken());
			}
		}

		if (!tokensToRemove.isEmpty()) {
			accessor.removeUserPushNotificationTokens(tokensToRemove);
		}

		return response;
	}

	@Override
	public String toString() {
		return "UserPushNotificationServiceImpl [pushNotificationService=" + this.pushNotificationService
		        + ", notificationSettingsTokenService=" + this.notificationSettingsTokenService + "]";
	}

}
