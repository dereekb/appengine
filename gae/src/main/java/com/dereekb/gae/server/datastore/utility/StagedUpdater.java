package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.Updater;

/**
 * {@link Updater} that performs saving immediately but can delay any secondary
 * changes until {@link #finishChangesWithEntities()} is called.
 * <p>
 * This is useful for batching TaskQueue requests.
 * 
 * @author dereekb
 *
 */
public interface StagedUpdater<T>
        extends StagedTransactionChange, Updater<T> {}
