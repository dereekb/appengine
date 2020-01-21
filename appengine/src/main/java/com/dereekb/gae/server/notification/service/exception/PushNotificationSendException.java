package com.dereekb.gae.server.notification.service.exception;

/**
 * Thrown when a push notification fails to send.
 *
 * @author dereekb
 *
 */
public class PushNotificationSendException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PushNotificationSendException() {
		super();
	}

	public PushNotificationSendException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PushNotificationSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public PushNotificationSendException(String message) {
		super(message);
	}

	public PushNotificationSendException(Throwable cause) {
		super(cause);
	}

	public PushNotificationSendExceptionType getSendExceptionType() {
		return PushNotificationSendExceptionType.EXCEPTION;
	}

}
