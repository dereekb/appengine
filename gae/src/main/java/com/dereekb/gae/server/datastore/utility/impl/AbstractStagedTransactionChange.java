package com.dereekb.gae.server.datastore.utility.impl;

import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;

/**
 * Abstract {@link StagedTransactionChange} implementation.
 * 
 * @author dereekb
 */
public abstract class AbstractStagedTransactionChange
        implements StagedTransactionChange {

	private boolean complete = false;

	// MARK: StagedUpdater
	@Override
	public void finishChanges() throws StagedTransactionAlreadyFinishedException {
		if (this.complete) {
			throw new StagedTransactionAlreadyFinishedException();
		}

		this.complete = true;
	}

	protected abstract void finishChangesWithEntities();

}
