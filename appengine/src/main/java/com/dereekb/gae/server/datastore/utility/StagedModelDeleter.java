package com.dereekb.gae.server.datastore.utility;

import com.dereekb.gae.server.datastore.ModelDeleter;

/**
 * {@link ModelDeleter} that performs deleting immediately but can delay any
 * secondary
 * changes until {@link #finishChangesWithEntities()} is called.
 * <p>
 * This is useful for batching TaskQueue requests.
 * 
 * @author dereekb
 *
 */
public interface StagedModelDeleter<T>
        extends StagedTransactionChange, ModelDeleter<T> {}
