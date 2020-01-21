package com.dereekb.gae.server.datastore.utility.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.exception.UpdateUnkeyedEntityException;
import com.dereekb.gae.server.datastore.utility.StagedTransactionAlreadyFinishedException;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;
import com.dereekb.gae.server.datastore.utility.StagedUpdaterFactory;
import com.dereekb.gae.utilities.collections.IteratorUtility;

/**
 * {@link StagedUpdaterFactory} that performs no operations.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class NoopStagedUpdaterFactory<T>
        implements StagedUpdaterFactory<T> {

	@Override
	public StagedUpdater<T> makeUpdater() {
		return new NoopStagedUpdaterImpl();
	}

	// MARK: StagedUpdater
	private class NoopStagedUpdaterImpl
	        implements StagedUpdater<T> {

		@Override
		public void finishChanges() throws StagedTransactionAlreadyFinishedException {
			// Do nothing.
		}

		@Override
		public boolean update(T entity) throws UpdateUnkeyedEntityException {
			return true;
		}

		@Override
		public List<T> update(Iterable<T> entities) throws UpdateUnkeyedEntityException {
			return IteratorUtility.iterableToList(entities);
		}

		@Override
		public boolean updateAsync(T entity) throws UpdateUnkeyedEntityException {
			return true;
		}

		@Override
		public List<T> updateAsync(Iterable<T> entities) throws UpdateUnkeyedEntityException {
			return IteratorUtility.iterableToList(entities);
		}

	}

}
