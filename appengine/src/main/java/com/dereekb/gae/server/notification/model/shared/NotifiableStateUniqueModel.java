package com.dereekb.gae.server.notification.model.shared;

import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link UniqueModel} that implements {@link NotifiableState}. Its state can also be directly updated.
 *
 * @author dereekb
 *
 */
public interface NotifiableStateUniqueModel<T extends NotifiableStateCode>
        extends UniqueModel, NotifiableState<T> {

	/**
	 * Sets the next notification state.
	 *
	 * @param notifiableState
	 *            {@link NotifiableState}. Never {@code null}.
	 */
	public void setNotifiableState(T notifiableState);

}
