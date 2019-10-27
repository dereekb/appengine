package com.dereekb.gae.server.notification.service.exception;

/**
 * {@link PushNotificationSendException} type.
 *
 * @author dereekb
 *
 */
public enum PushNotificationSendExceptionType {

	/**
	 * General exception
	 */
	EXCEPTION,

	/**
	 * System configuration is invalid.
	 */
	SYSTEM_CONFIGURATION_EXCEPTION,

	/**
	 * System was unavailable.
	 */
	SYSTEM_UNAVAILABLE_EXCEPTION,

	/**
	 * The token was deemed invalid by the remote system.
	 */
	INVALID_TOKEN_EXCEPTION

}
