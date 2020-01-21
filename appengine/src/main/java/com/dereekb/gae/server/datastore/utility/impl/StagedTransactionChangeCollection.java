package com.dereekb.gae.server.datastore.utility.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.utilities.collections.list.ListUtility;

/**
 * {@link StagedTransactionChange} that contains several other changes.
 *
 * @author dereekb
 *
 */
public class StagedTransactionChangeCollection extends AbstractStagedTransactionChange
        implements StagedTransactionChange {

	private boolean ignoreAlreadyFinished = false;
	private List<StagedTransactionChange> changes;

	public StagedTransactionChangeCollection() {
		this(new ArrayList<StagedTransactionChange>());
	}

	public StagedTransactionChangeCollection(StagedTransactionChange... changes) {
		this(ListUtility.toList(changes));
	}

	public StagedTransactionChangeCollection(List<StagedTransactionChange> changes) {
		super();
		this.setChanges(changes);
	}

	public static StagedTransactionChangeCollection make() {
		return new StagedTransactionChangeCollection();
	};

	public static StagedTransactionChangeCollection make(StagedTransactionChange... changes) {
		return new StagedTransactionChangeCollection(changes);
	}

	public boolean isIgnoreAlreadyFinished() {
		return this.ignoreAlreadyFinished;
	}

	public void setIgnoreAlreadyFinished(boolean ignoreAlreadyFinished) {
		this.ignoreAlreadyFinished = ignoreAlreadyFinished;
	}

	public List<StagedTransactionChange> getChanges() {
		return this.changes;
	}

	public boolean isEmpty() {
		return this.changes.isEmpty();
	}

	/**
	 * Adds all changes to the collection.
	 *
	 * @param changes
	 *            {@link Iterable}. Never {@code null}.
	 */
	public void addAllChange(Iterable<StagedTransactionChange> changes) {
		for (StagedTransactionChange change : changes) {
			this.addChange(change);
		}
	}

	/**
	 * Adds the change to the collection. If {@code null}, it is ignored.
	 *
	 * @param change
	 *            {@link StagedTransactionChange}. Can be {@code null}.
	 * @throws StagedTransactionAlreadyFinishedException
	 *             thrown if the changes have already been finished.
	 */
	public void addChange(StagedTransactionChange change) throws StagedTransactionAlreadyFinishedException {
		if (change != null) {
			if (!this.ignoreAlreadyFinished) {
				this.assertIsNotComplete();
			}

			this.changes.add(change);
		}
	}

	public void setChanges(List<StagedTransactionChange> changes) {
		if (changes == null) {
			throw new IllegalArgumentException("changes cannot be null.");
		}

		this.resetComplete();
		this.changes = ListUtility.copy(changes);
	}

	// MARK: AbstractStagedTransactionChange
	@Override
	protected void finishChangesWithEntities() {
		finishChanges(this.changes, this.ignoreAlreadyFinished);
	}

	// MARK: Utility
	public static void finishChanges(List<StagedTransactionChange> changes)
	        throws StagedTransactionAlreadyFinishedException {
		finishChanges(changes, false);
	}

	public static void finishChanges(List<StagedTransactionChange> changes,
	                                 boolean ignoreAlreadyFinished)
	        throws StagedTransactionAlreadyFinishedException {

		for (StagedTransactionChange change : changes) {
			try {
				change.finishChanges();
			} catch (StagedTransactionAlreadyFinishedException e) {
				if (!ignoreAlreadyFinished) {
					throw e;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "StagedTransactionChangeCollection [ignoreAlreadyFinished=" + this.ignoreAlreadyFinished + ", changes="
		        + this.changes + ", isComplete()=" + this.isComplete() + "]";
	}

}
