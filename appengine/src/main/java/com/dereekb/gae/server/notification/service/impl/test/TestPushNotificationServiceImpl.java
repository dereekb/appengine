package com.dereekb.gae.server.notification.service.impl.test;

import java.util.Collections;
import java.util.logging.Logger;

import com.dereekb.gae.server.notification.service.PushNotificationSendRequest;
import com.dereekb.gae.server.notification.service.PushNotificationSendResponse;
import com.dereekb.gae.server.notification.service.PushNotificationService;
import com.dereekb.gae.server.notification.service.PushNotificationTokenUtility;
import com.dereekb.gae.server.notification.service.exception.PushNotificationSendException;
import com.dereekb.gae.server.notification.service.impl.PushNotificationSendResponseImpl;
import com.dereekb.gae.utilities.gae.GoogleAppEngineUtility;

/**
 * {@link PushNotificationService} implementation that logs a message.
 *
 * @author dereekb
 *
 */
public class TestPushNotificationServiceImpl
        implements PushNotificationService {

	private static final Logger LOGGER = Logger.getLogger(TestPushNotificationServiceImpl.class.getName());

	public static PushNotificationService makeForEnvironment(PushNotificationService service) {
		return makeForEnvironment(service, false);
	}

	public static PushNotificationService makeForEnvironment(PushNotificationService service,
	                                                         boolean productionOnly) {
		boolean useTestDelegate = false;

		switch (GoogleAppEngineUtility.getEnvironmentType()) {
			case DEVELOPMENT:
				useTestDelegate = productionOnly;
				break;
			case PRODUCTION:
				useTestDelegate = false;
				break;
			case UNIT_TESTING:
			default:
				useTestDelegate = true;
				break;
		}

		if (useTestDelegate) {
			return new TestPushNotificationServiceImpl();
		} else {
			return service;
		}
	}

	// MARK: PushNotificationService
	@Override
	public PushNotificationSendResponse sendNotification(PushNotificationSendRequest request)
	        throws PushNotificationSendException {

		StringBuilder builder = new StringBuilder();

		builder.append("-- Development Push Notification --\n\n");
		builder.append(
		        "Tokens: " + PushNotificationTokenUtility.readTokenStrings(request.getTokenSet().getTokens()) + "\n\n");
		builder.append("Title: " + request.getTitle() + "\n\n");
		builder.append("Message: " + request.getMessage() + "\n\n");
		builder.append("Image: " + request.getImageUrl() + "\n\n");
		builder.append("Data: " + ((request.getData() != null) ? request.getData().getParameters() : null) + "\n\n");

		String message = builder.toString();
		LOGGER.info(message);

		PushNotificationSendResponse response = new PushNotificationSendResponseImpl(request.getTokenSet().getTokens(),
		        Collections.emptyList());
		return response;
	}

}
