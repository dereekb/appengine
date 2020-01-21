package com.dereekb.gae.server.notification.service.exception;

/**
 * Thrown when a push notification fails due to system configuration issues.
 *
 * @author dereekb
 *
 */
public class SystemConfigurationPushNotificationSendException extends PushNotificationSendException {

	private static final long serialVersionUID = 1L;

	public SystemConfigurationPushNotificationSendException() {
		super();
	}

	public SystemConfigurationPushNotificationSendException(String message,
	        Throwable cause,
	        boolean enableSuppression,
	        boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SystemConfigurationPushNotificationSendException(String message, Throwable cause) {
		super(message, cause);
	}

	public SystemConfigurationPushNotificationSendException(String message) {
		super(message);
	}

	public SystemConfigurationPushNotificationSendException(Throwable cause) {
		super(cause);
	}

	@Override
	public PushNotificationSendExceptionType getSendExceptionType() {
		return PushNotificationSendExceptionType.SYSTEM_CONFIGURATION_EXCEPTION;
	}

}
