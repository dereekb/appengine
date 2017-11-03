package com.dereekb.gae.server.datastore.utility;

/**
 * Interface that performs the changes immediately but can delay any secondary
 * changes until {@link #finishChanges()} is called.
 * <p>
 * This is useful for batching Taskqueue requests in instances where the work
 * may fail.
 * 
 * @author dereekb
 *
 */
public abstract interface StagedTransactionChange {

	/**
	 * Finishes the changes. Should only be called once.
	 * 
	 * @throws StagedTransactionAlreadyFinishedException
	 *             thrown if this function has already been called.
	 */
	public void finishChanges() throws StagedTransactionAlreadyFinishedException;

}
