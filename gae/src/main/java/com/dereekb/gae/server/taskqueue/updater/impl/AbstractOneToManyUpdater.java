package com.dereekb.gae.server.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;

/**
 * {@link RelatedModelUpdater} implementation used for a one-to-many relation.
 * 
 * @author dereekb
 *
 * @param <T>
 *            "many" model type
 * @param <R>
 *            "one" model type
 */
public abstract class AbstractOneToManyUpdater<T extends UniqueModel, R extends UniqueModel>
        implements RelatedModelUpdater<T> {

	@Override
	public RelatedModelUpdaterResult updateRelations(RelatedModelUpdateType change,
	                                                 Iterable<T> models) {
		Instance<T> instance = this.makeInstance(change);
		return instance.performChanges(models);
	}

	/**
	 * Creates a new {@link Instance}.
	 * 
	 * @param change
	 *            {@link RelatedModelUpdateType}. Never {@code null}.
	 * @return {@link RelatedModelUpdaterResult}. Never {@code null}.
	 */
	protected abstract <I extends Instance<T>> I makeInstance(RelatedModelUpdateType change);

	protected interface Instance<T> {

		public RelatedModelUpdaterResult performChanges(Iterable<T> models);

	}

	/**
	 * Used for rolling changes.
	 * 
	 * @author dereekb
	 *
	 */
	protected interface InstanceChange<R, C extends InstanceChange<R, C>> {

		/**
		 * Returns the relation key.
		 * 
		 * @return {@link ModelKey}. Never {@code null}.
		 */
		public ModelKey getRelationKey();

		/**
		 * Merges the changes together into a new single instance.
		 * 
		 * @param change
		 *            {@link InstanceChange}. Never {@code null}.
		 * @return {@link InstanceChange}. Never {@code null}.
		 */
		public C merge(C change);

		/**
		 * Applies the changes to the relation model instance.
		 * 
		 * @param relationModel
		 *            Relation model. Never {@code null}.
		 */
		public void applyChanges(R relationModel);

	}

	protected abstract class AbstractInstance<O extends RelatedModelUpdaterResult, C extends InstanceChange<R, C>>
	        implements Instance<T> {

		// Changer used for building the changes.
		protected final int INSTANCE_BATCH_SIZE = 10;
		protected final Partitioner PARTITIONER = new PartitionerImpl(this.INSTANCE_BATCH_SIZE);

		// MARK: Instance
		@Override
		public final O performChanges(Iterable<T> models) {
			List<C> changes = new ArrayList<C>();
			HashMapWithList<ModelKey, T> map = this.buildRelationMap(models);

			// Perform changes for each overview independently.
			for (Entry<ModelKey, List<T>> entry : map.entrySet()) {
				C change = this.performChangesForRelationKey(entry.getKey(), entry.getValue());
				changes.add(change);
			}

			return this.makeOutputForChanges(changes);
		}

		protected abstract O makeOutputForChanges(List<C> changes);

		// MARK: Internal
		protected C performChangesForRelationKey(ModelKey relationKey,
		                                         List<T> models) {
			C change = this.buildInstanceChanges(relationKey, models);

			// TODO: Do change.

			/*
			 * ObjectifyService.ofy().transactNew(new VoidWork() {
			 * 
			 * @Override
			 * public void vrun() {
			 * try {
			 * TallyOverview overview =
			 * TallyOverviewUpdaterImpl.this.loader.getOverview(tallyOverviewKey
			 * );
			 * changes.applyChanges(overview);
			 * 
			 * TallyOverviewUpdaterFactoryImpl.this.overviewUpdater.update(
			 * overview);
			 * } catch (NoTallyOverviewException e) {
			 * // Overview has been deleted. Ignore exception.
			 * }
			 * }
			 * 
			 * });
			 */

			return change;
		}

		protected C buildInstanceChanges(ModelKey relationKey,
		                                 List<T> models) {
			C rollingChanges = this.makeInitialInstanceChange(relationKey);

			List<List<T>> partitions = this.PARTITIONER.makePartitions(models);

			for (List<T> partition : partitions) {
				C change = this.buildInstanceChangeWithPartition(relationKey, partition);
				rollingChanges = rollingChanges.merge(change);
			}

			return rollingChanges;
		}

		protected abstract C makeInitialInstanceChange(ModelKey relationKey);

		protected abstract C buildInstanceChangeWithPartition(ModelKey relationKey,
		                                                      List<T> partition);

		// MARK: Relation Map
		private HashMapWithList<ModelKey, T> buildRelationMap(Iterable<T> models) {
			HashMapWithList<ModelKey, T> map = new HashMapWithList<ModelKey, T>();

			for (T model : models) {
				ModelKey relationModelKey = this.getRelationModelKey(model);
				map.add(relationModelKey, model);
			}

			return map;
		}

		protected abstract ModelKey getRelationModelKey(T model);

	}

}
