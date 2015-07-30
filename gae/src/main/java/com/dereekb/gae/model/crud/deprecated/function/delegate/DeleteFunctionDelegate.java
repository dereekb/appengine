package com.dereekb.gae.model.crud.deprecated.function.delegate;

import com.dereekb.gae.model.crud.deprecated.function.DeleteFunction;
import com.dereekb.gae.model.crud.exception.CancelDeleteException;

/**
 * Delete delegate for type T.
 *
 * @author dereekb
 *
 * @param <T>
 * @see {@link DeleteFunction}
 */
@Deprecated
public interface DeleteFunctionDelegate<T> {

	/**
	 * Prepares the items for deletion, but doesn't actually remove the objects
	 * from the datastore.
	 *
	 * @param objects
	 * @throws CancelDeleteException
	 *             if the delegate decides to cancel the delete. The system must
	 *             maintain the state it was at before the delete occured; that
	 *             is, nothing should have changed.
	 */
	public void deleteObjects(Iterable<T> objects) throws CancelDeleteException;

}
