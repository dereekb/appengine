package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.ForceStorer;
import com.dereekb.gae.server.datastore.Storer;

/**
 * {@link Storer} that performs saving immediately but can delay any secondary
 * changes until {@link #finishChangesWithEntities()} is called.
 * <p>
 * {@link ForceStorer} functionality is available for special cases. Use with
 * caution.
 * <p>
 * This is useful for batching TaskQueue requests.
 *
 * @author dereekb
 *
 */
public interface StagedStorer<T>
        extends StagedTransactionChange, ForceStorer<T> {}
