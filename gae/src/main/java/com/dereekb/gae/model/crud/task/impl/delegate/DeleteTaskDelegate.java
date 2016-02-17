package com.dereekb.gae.model.crud.task.impl.delegate;

import com.dereekb.gae.model.crud.exception.CancelDeleteException;
import com.dereekb.gae.model.crud.task.impl.DeleteTaskImpl;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * {@link DeleteTaskImpl} delegate.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface DeleteTaskDelegate<T extends UniqueModel> {

	/**
	 * Prepares the items for deletion.
	 * <p>
	 * If one or more of the objects are not ready to be deleted, this function
	 * cancels.
	 *
	 * @param input
	 *            input to delete. Never {@code null}.
	 * @throws CancelDeleteException
	 *             if the delegate decides to cancel the delete. The system must
	 *             maintain the state it was at before the delete occurred; that
	 *             is, nothing should have changed.
	 */
	public void deleteObjects(Iterable<T> input) throws CancelDeleteException;

}
