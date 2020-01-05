package com.dereekb.gae.server.notification.model.shared;

/**
 * Interface for models that can be marked "notified", typically through the
 * push notification system.
 *
 * @author dereekb
 *
 * @see {@link NotifiableStateCode} for models that may have multiple
 *      notifications types.
 */
public interface Notifiable {

	/**
	 * Whether or not this model has been notified.
	 *
	 * @return {@code true} if a notification has been sent.
	 */
	public boolean isNotified();

}
