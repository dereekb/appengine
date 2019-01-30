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

	public boolean isComplete() {
		return this.complete;
	}

	protected void resetComplete() {
		this.complete = false;
	}

	// MARK: StagedUpdater
	@Override
	public final void finishChanges() throws StagedTransactionAlreadyFinishedException {
		this.assertIsNotComplete();
		this.finishChangesWithEntities();
		this.complete = true;
	}

	protected void assertIsNotComplete() {
		if (this.complete) {
			throw new StagedTransactionAlreadyFinishedException();
		}
	}

	protected abstract void finishChangesWithEntities();

}
