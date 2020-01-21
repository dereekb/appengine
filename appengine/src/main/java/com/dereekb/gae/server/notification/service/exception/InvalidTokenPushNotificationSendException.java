package com.dereekb.gae.server.notification.service.exception;

/**
 * Thrown when a push notification token is expired or invalid.
 *
 * @author dereekb
 *
 */
public class InvalidTokenPushNotificationSendException extends PushNotificationSendException {

	private static final long serialVersionUID = 1L;

	public InvalidTokenPushNotificationSendException() {
		super();
	}

	public InvalidTokenPushNotificationSendException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public InvalidTokenPushNotificationSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTokenPushNotificationSendException(String message) {
		super(message);
	}

	public InvalidTokenPushNotificationSendException(Throwable cause) {
		super(cause);
	}

	@Override
	public PushNotificationSendExceptionType getSendExceptionType() {
		return PushNotificationSendExceptionType.INVALID_TOKEN_EXCEPTION;
	}

}
