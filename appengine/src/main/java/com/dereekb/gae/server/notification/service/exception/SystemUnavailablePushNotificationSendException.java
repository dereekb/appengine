package com.dereekb.gae.server.notification.service.exception;

/**
 * Thrown when a push notification fails due to the remote system being unavailable.
 *
 * @author dereekb
 *
 */
public class SystemUnavailablePushNotificationSendException extends PushNotificationSendException {

	private static final long serialVersionUID = 1L;

	public SystemUnavailablePushNotificationSendException() {
		super();
	}

	public SystemUnavailablePushNotificationSendException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemUnavailablePushNotificationSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemUnavailablePushNotificationSendException(String message) {
		super(message);
	}

	public SystemUnavailablePushNotificationSendException(Throwable cause) {
		super(cause);
	}

	@Override
	public PushNotificationSendExceptionType getSendExceptionType() {
		return PushNotificationSendExceptionType.SYSTEM_UNAVAILABLE_EXCEPTION;
	}

}
