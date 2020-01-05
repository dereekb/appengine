package com.dereekb.gae.model.taskqueue.updater.impl;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.impl.StagedTransactionChangeCollection;

/**
 * {@link RelatedModelUpdaterResult} implementation.
 *
 * @author dereekb
 *
 */
public class RelatedModelUpdaterResultImpl
        implements RelatedModelUpdaterResult {

	private final StagedTransactionChangeCollection changes;

	public RelatedModelUpdaterResultImpl() {
		this(StagedTransactionChangeCollection.make());
	}

	public RelatedModelUpdaterResultImpl(StagedTransactionChange change) {
		this(StagedTransactionChangeCollection.make(change));
	}

	public RelatedModelUpdaterResultImpl(StagedTransactionChangeCollection changes) {
		super();

		if (changes == null) {
			throw new IllegalArgumentException("changes cannot be null.");
		}

		this.changes = changes;
	}

	public StagedTransactionChangeCollection getChanges() {
		return this.changes;
	}

	// MARK: StagedTransactionChange
	@Override
	public void finishChanges() throws StagedTransactionAlreadyFinishedException {
		this.changes.finishChanges();
	}

}