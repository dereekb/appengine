package com.dereekb.gae.model.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.dereekb.gae.model.exception.UnavailableModelException;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.dereekb.gae.utilities.filters.Filter;
import com.dereekb.gae.utilities.filters.FilterResults;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * {@link RelatedModelUpdater} implementation used for a one-to-many relation
 * with the input model type being the "many".
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
public abstract class AbstractManyToOneUpdater<T extends UniqueModel, R extends UniqueModel> extends AbstractUpdater<T> {

	private Getter<T> modelGetter;

	public AbstractManyToOneUpdater(Getter<T> modelGetter) {
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

	/**
	 * {@link AbstractInstance} extension that iterates over each model
	 * individually, filtering out ones that don't matter, and updating the
	 * changes.
	 * 
	 * @author dereekb
	 *
	 * @param <O>
	 *            output type
	 * @param <C>
	 *            changes type
	 */
	protected abstract class FilteredOneByOneAbstractInstance<O extends RelatedModelUpdaterResult, C extends InstanceChange<R, C>> extends OneByOneAbstractInstance<O, C> {

		private final Filter<T> filter;

		public FilteredOneByOneAbstractInstance(Filter<T> filter) {
			super();
			this.filter = filter;
		}

		// MARK: AbstractInstance
		@Override
		protected C performModelChangesWithPartition(ModelKey relationKey,
		                                             List<T> partition) {
			FilterResults<T> results = this.filterModels(partition);
			List<T> applicable = results.getPassingObjects();

			C changes = super.performModelChangesWithPartition(relationKey, applicable);
			this.updateChangesForFilteredModels(changes, results);

			return changes;
		}

		protected FilterResults<T> filterModels(List<T> partition) {
			return this.filter.filterObjects(partition);
		}

		protected void updateChangesForFilteredModels(C changes,
		                                              FilterResults<T> results) {
			// Do nothing by default.
		}

	}

	protected abstract class AbstractOneByOneInstanceChange<C extends AbstractOneByOneInstanceChange<C>> extends AbstractInstanceChange<C> {

		protected final List<ModelKey> skippedValues = new ArrayList<ModelKey>();
		protected final List<ModelKey> updatedValues = new ArrayList<ModelKey>();
		protected final Set<ModelKey> removedValues = new HashSet<ModelKey>();

		public AbstractOneByOneInstanceChange(ModelKey relationKey) {
			super(relationKey);
		}

		public AbstractOneByOneInstanceChange(AbstractOneByOneInstanceChange<C> change) {
			super(change);
			this.skippedValues.addAll(change.skippedValues);
			this.updatedValues.addAll(change.updatedValues);
			this.removedValues.addAll(change.removedValues);
		}

		public List<ModelKey> getSkippedValues() {
			return this.skippedValues;
		}

		public List<ModelKey> getUpdatedValues() {
			return this.updatedValues;
		}

		public Set<ModelKey> getRemovedValues() {
			return this.removedValues;
		}

		protected void addUpdatedValue(T updated) {
			this.updatedValues.add(updated.getModelKey());
		}

		protected void addRemovedValue(T removed) {
			this.removedValues.add(removed.getModelKey());
		}

		public void addSkippedValues(Collection<T> skipped) {
			this.skippedValues.addAll(ModelKey.readModelKeys(skipped));
		}

	}

	/**
	 * {@link AbstractInstance} extension that updates a single Changes object
	 * using the models, one by one.
	 * <p>
	 * Models that are updated are saved.
	 * 
	 * @author dereekb
	 *
	 * @param <O>
	 *            output type
	 * @param <C>
	 *            changes type
	 */
	protected abstract class OneByOneAbstractInstance<O extends RelatedModelUpdaterResult, C extends InstanceChange<R, C>> extends AbstractInstance<O, C> {

		// MARK: AbstractInstance
		@Override
		protected C performModelChangesWithPartition(ModelKey relationKey,
		                                             List<T> partition) {
			C changes = this.makeInitialInstanceModelChange(relationKey);
			List<T> updated = new ArrayList<T>(partition.size());

			for (T model : partition) {
				if (this.updateChangesWithModel(changes, model)) {
					updated.add(model);
				}
			}

			this.saveUpdatedModels(updated);
			return changes;
		}

		/**
		 * Updates the input changes, and the optionally, the input model.
		 * 
		 * @param changes
		 *            Changes. Never {@code null}.
		 * @param model
		 *            Model. Never {@code null}.
		 * @return {@code true} if the input model was modified.
		 */
		protected abstract boolean updateChangesWithModel(C changes,
		                                                  T model);

		protected abstract void saveUpdatedModels(List<T> updated);

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
		 * @return {@code true} if the relation model was modified and must be
		 *         saved.
		 */
		public boolean applyChanges(R relationModel);

	}

	protected abstract class AbstractInstanceChange<C extends InstanceChange<R, C>>
	        implements InstanceChange<R, C> {

		private final ModelKey relationKey;

		public AbstractInstanceChange(InstanceChange<R, C> change) {
			this(change.getRelationKey());
		}

		public AbstractInstanceChange(ModelKey relationKey) {
			this.relationKey = relationKey;
		}

		@Override
		public ModelKey getRelationKey() {
			return this.relationKey;
		}

	}

	protected abstract class AbstractInstance<O extends RelatedModelUpdaterResult, C extends InstanceChange<R, C>>
	        implements Instance<T> {

		/**
		 * This batch size limit is related to the Google App Engine
		 * transactional limit of 25 entities/transaction.
		 */
		protected final int INSTANCE_BATCH_SIZE = 10;
		protected final Partitioner PARTITIONER = new PartitionerImpl(this.INSTANCE_BATCH_SIZE);

		// MARK: Instance
		@Override
		public final O performChanges(Iterable<T> models) {
			List<C> changes = new ArrayList<C>();
			HashMapWithList<ModelKey, T> map = this.buildRelationMap(models);

			// Perform changes for each relation object independently.
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
		protected C performRelationModelUpdate(R relation,
		                                       C changes) {
			if (changes.applyChanges(relation)) {
				this.saveUpdatedRelationModel(relation);
			}

			return changes;
		}

		/**
		 * Saves the relation model changes.
		 * 
		 * @param relation
		 */
		protected abstract void saveUpdatedRelationModel(R relation);

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
					List<T> reloadedPartition = AbstractInstance.this.reloadPartition(partition);
					List<T> filteredPartition = AbstractInstance.this.filterPartitionModels(reloadedPartition);
					return AbstractInstance.this.performModelChangesWithPartition(relationKey, filteredPartition);
				}

			});
		}

		/**
		 * Reloads the models partition for a transaction.
		 * 
		 * @param partition
		 *            {@link List}. Never {@code null}.
		 * @return {@link List}. Never {@code null}.
		 */
		protected List<T> reloadPartition(final List<T> partition) {
			return AbstractManyToOneUpdater.this.modelGetter.get(partition);
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

		/**
		 * Prepares all necessary changes. Updates to elements in the partition
		 * may be performed if necessary.
		 * 
		 * @param relationKey
		 *            {@link ModelKey} for the relation. Never {@code null}.
		 * @param partition
		 *            Partition of models. Never {@code null}.
		 * @return Changes object. Never {@code null}.
		 */
		protected abstract C performModelChangesWithPartition(ModelKey relationKey,
		                                                      List<T> partition);

		// MARK: Internal - Results
		protected abstract O makeOutputForChanges(List<C> changes);

	}

}
