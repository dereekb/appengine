package com.dereekb.gae.server.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdaterFactory;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdaterResult;

/**
 * {@link RelatedModelUpdaterFactory} implementation that wraps several other
 * factories. The results are all merged together.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class MultiRelatedModelUpdaterFactory<T extends UniqueModel>
        implements RelatedModelUpdaterFactory<T> {

	private List<? extends RelatedModelUpdaterFactory<T>> factories;

	public MultiRelatedModelUpdaterFactory(List<? extends RelatedModelUpdaterFactory<T>> factories) {
		this.setFactories(factories);
	}

	public List<? extends RelatedModelUpdaterFactory<T>> getFactories() {
		return this.factories;
	}

	public void setFactories(List<? extends RelatedModelUpdaterFactory<T>> factories) {
		if (factories == null) {
			throw new IllegalArgumentException("factories cannot be null.");
		}

		this.factories = factories;
	}

	// MARK: RelatedModelUpdaterFactory<T>
	@Override
	public RelatedModelUpdater<T> makeUpdater() {
		return new RelatedModelUpdaterImpl();
	}

	// MARK: RelatedModelUpdater
	private class RelatedModelUpdaterImpl
	        implements RelatedModelUpdater<T> {

		@Override
		public RelatedModelUpdaterResult updateRelations(RelatedModelUpdateType change,
		                                                 Iterable<T> models) {

			RelatedModelUpdaterResult results = null;

			List<? extends RelatedModelUpdater<T>> updaters = this.makeUpdaters();

			for (RelatedModelUpdater<T> updater : updaters) {
				// TODO: Merge results of all updaters together.
				updater.updateRelations(change, models);
			}

			return results;
		}

		private List<? extends RelatedModelUpdater<T>> makeUpdaters() {
			List<RelatedModelUpdater<T>> updaters = new ArrayList<>();

			for (RelatedModelUpdaterFactory<T> factory : MultiRelatedModelUpdaterFactory.this.factories) {
				RelatedModelUpdater<T> updater = factory.makeUpdater();
				updaters.add(updater);
			}

			return updaters;
		}

	}

}
