package com.dereekb.gae.server.notification.model.shared;

/**
 * Interface for models that are notified but have multiple states, instead of a single one.
 *
 * @return
 */
public interface NotifiableState<T extends NotifiableStateCode> {

	/**
	 * Returns the notifiable model.
	 *
	 * @return
	 */
	public T getNotifiableState();

}
