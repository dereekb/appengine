package com.dereekb.gae.model.taskqueue.updater.impl;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterFactory;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.models.UniqueModel;

/**
 * Abstract {@link RelatedModelUpdater} implementation that uses an internally
 * generated Instance to perform changes.
 * <p>
 * Also implements {@link RelatedModelUpdaterFactory} by default, allowing it to
 * return itself through the factory method since it uses an instance internally
 * for each request.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractUpdater<T extends UniqueModel>
        implements RelatedModelUpdater<T>, RelatedModelUpdaterFactory<T> {

	// MARK: RelatedModelUpdaterFactory
	@Override
	public RelatedModelUpdater<T> makeUpdater() {
		return this;
	}

	// MARK: RelatedModelUpdater
	@Override
	public RelatedModelUpdaterResult updateRelations(RelatedModelUpdateType change,
	                                                 Iterable<T> models) {
		Instance<T> instance = this.makeInstance(change);
		RelatedModelUpdaterResult result = instance.performChanges(models);
		this.finishChanges(instance, result);
		return result;
	}

	protected void finishChanges(Instance<T> instance,
	                             RelatedModelUpdaterResult result) {
		if (result != null) {
			result.finishChanges();
		}
	}

	/**
	 * Creates a new {@link Instance}.
	 *
	 * @param change
	 *            {@link RelatedModelUpdateType}. Never {@code null}.
	 * @return {@link RelatedModelUpdaterResult}. Never {@code null}.
	 */
	protected abstract Instance<T> makeInstance(RelatedModelUpdateType change);

	protected interface Instance<T> {

		public RelatedModelUpdaterResult performChanges(Iterable<T> models);

	}

}
