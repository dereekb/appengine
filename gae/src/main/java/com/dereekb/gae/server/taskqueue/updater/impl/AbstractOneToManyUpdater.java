package com.dereekb.gae.server.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdateType;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.server.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * {@link RelatedModelUpdater} implementation used for a one-to-many relation.
 * <p>
 * Modified elements are reloaded before attempting to be modified.
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

	private Getter<T> modelGetter;

	public AbstractOneToManyUpdater(Getter<T> modelGetter) {
		this.setModelGetter(modelGetter);
	}

	public Getter<T> getModelGetter() {
		return this.modelGetter;
	}

	public void setModelGetter(Getter<T> modelGetter) {
		if (modelGetter == null) {
			throw new IllegalArgumentException("modelGetter cannot be null.");
		}

		this.modelGetter = modelGetter;
	}

	// MARK: RelatedModelUpdater
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
	protected abstract Instance<T> makeInstance(RelatedModelUpdateType change);

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

		// MARK: Internal - Relation Map
		private HashMapWithList<ModelKey, T> buildRelationMap(Iterable<T> models) {
			HashMapWithList<ModelKey, T> map = new HashMapWithList<ModelKey, T>();

			for (T model : models) {
				ModelKey relationModelKey = this.getRelationModelKey(model);
				map.add(relationModelKey, model);
			}

			return map;
		}

		protected abstract ModelKey getRelationModelKey(T model);

		// MARK: Internal - Relation Changes
		protected C performChangesForRelationKey(ModelKey relationKey,
		                                         List<T> models) {
			final C changes = this.buildInstanceModelChanges(relationKey, models);

			return this.performSafeRelationModelUpdate(changes);
		}

		private C performSafeRelationModelUpdate(final C changes) {
			final ModelKey relationKey = changes.getRelationKey();

			return ObjectifyService.ofy().transactNew(new Work<C>() {

				@Override
				public C run() {
					C finalChanges = null;

					try {
						R relation = AbstractInstance.this.loadRelation(relationKey);
						finalChanges = AbstractInstance.this.performRelationModelUpdate(relation, changes);
					} catch (UnavailableModelException e) {
						// Model is unavailable/has been deleted. Ignore and do
						// nothing.
					}

					if (finalChanges == null) {
						finalChanges = changes;
					}

					return finalChanges;
				}

			});
		}

		protected abstract R loadRelation(ModelKey relationKey) throws UnavailableModelException;

		/**
		 * Updates the relation object and saves the changes.
		 * 
		 * @param relation
		 *            Relation. Never {@code null}.
		 * @param changes
		 *            Changes. Never {@code null}.
		 * @return Changes. Never {@code null}.
		 */
		protected abstract C performRelationModelUpdate(R relation,
		                                                C changes);

		// MARK: Internal - Model Changes
		protected final C buildInstanceModelChanges(ModelKey relationKey,
		                                            List<T> models) {
			C rollingChanges = this.makeInitialInstanceModelChange(relationKey);

			List<List<T>> partitions = this.PARTITIONER.makePartitions(models);

			for (List<T> partition : partitions) {
				C change = this.performSafeModelChangesWithPartition(relationKey, partition);
				rollingChanges = rollingChanges.merge(change);
			}

			return rollingChanges;
		}

		protected abstract C makeInitialInstanceModelChange(ModelKey relationKey);

		/**
		 * Performs the changes within a new transaction to make sure the
		 * modified types have not been changed since being updated.
		 * <p>
		 * Directly calls
		 * {@link #performModelChangesWithPartition(ModelKey, List)};
		 */
		protected final C performSafeModelChangesWithPartition(final ModelKey relationKey,
		                                                       final List<T> partition) {
			return ObjectifyService.ofy().transactNew(new Work<C>() {

				@Override
				public C run() {
					List<T> reloadedPartition = AbstractOneToManyUpdater.this.modelGetter.get(partition);
					List<T> filteredPartition = AbstractInstance.this.filterPartitionModels(reloadedPartition);
					return AbstractInstance.this.performModelChangesWithPartition(relationKey, filteredPartition);
				}

			});
		}

		/**
		 * Override to perform filtering.
		 * 
		 * @param partition
		 *            Reloaded models. Never {@code null}.
		 * @return {@link List} of filtered models. Never {@code null}.
		 */
		protected List<T> filterPartitionModels(List<T> partition) {
			return partition;
		}

		protected abstract C performModelChangesWithPartition(ModelKey relationKey,
		                                                      List<T> partition);

		// MARK: Internal - Results
		protected abstract O makeOutputForChanges(List<C> changes);

	}

}
