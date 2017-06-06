package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.Updater;

/**
 * {@link Updater} that performs saving immediately but can delay any secondary
 * changes until {@link #finishUpdate()} is called.
 * <p>
 * This is useful for batching TaskQueue requests.
 * 
 * @author dereekb
 *
 */
public interface StagedUpdater<T>
        extends Updater<T> {

	/**
	 * Finishes the staged updater. Should only be called once.
	 * 
	 * @throws StagedUpdaterAlreadyFinishedException
	 *             thrown if the update has already been finished.
	 */
	public void finishUpdate() throws StagedUpdaterAlreadyFinishedException;

}
