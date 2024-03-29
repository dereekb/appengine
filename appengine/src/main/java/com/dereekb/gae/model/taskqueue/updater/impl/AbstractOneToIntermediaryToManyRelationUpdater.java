package com.dereekb.gae.model.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.dereekb.gae.model.extension.taskqueue.scheduler.builder.impl.AbstractKeyQueryIterateTaskRequestBuilder;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.map.HashMapWithList;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * Special {@link AbstractUpdater} that updates a relation between two types by
 * first retrieving an intermediate model using the input, then loading related
 * types based on the results. The related types are then used for updating
 * within a transaction. The related types have a many to one relation with the
 * input models.
 * <p>
 * The input models that have no knowledge of the "many" related type.
 * <p>
 * In most cases, the intermediate type will be queried for and
 * then used to load the multiple update types.
 * <p>
 * This is only for special cases.
 *
 * If you're attempting to use a model query to get relations, you might
 * consider leveraging taskqueue queries instead, such as a
 * {@link AbstractModelTransactionUpdater} that is scheduled via a scheduler and
 * a {@link AbstractKeyQueryIterateTaskRequestBuilder} related type. This gives
 * you more predictability and scalability as the taskqueue's query iterator
 * would be used for handling and traversing the query results.
 *
 * @author dereekb
 *
 * @param <T>
 *            "one" model type
 * @param <Q>
 *            intermediary model type
 * @param <R>
 *            "many" model type
 */
public abstract class AbstractOneToIntermediaryToManyRelationUpdater<T extends UniqueModel, N, R extends UniqueModel> extends AbstractUpdater<T> {

	private Getter<T> inputModelGetter;
	private Getter<R> relationModelGetter;

	public AbstractOneToIntermediaryToManyRelationUpdater(Getter<T> inputModelGetter, Getter<R> relationModelGetter) {
		this.setInputModelGetter(inputModelGetter);
		this.setRelationModelGetter(relationModelGetter);
	}

	public Getter<T> getInputModelGetter() {
		return this.inputModelGetter;
	}

	public void setInputModelGetter(Getter<T> inputModelGetter) {
		if (inputModelGetter == null) {
			throw new IllegalArgumentException("inputModelGetter cannot be null.");
		}

		this.inputModelGetter = inputModelGetter;
	}

	public Getter<R> getRelationModelGetter() {
		return this.relationModelGetter;
	}

	public void setRelationModelGetter(Getter<R> relationModelGetter) {
		if (relationModelGetter == null) {
			throw new IllegalArgumentException("relationModelGetter cannot be null.");
		}

		this.relationModelGetter = relationModelGetter;
	}

	/**
	 * {@link OneByOneAbstractInstance} that does not use changes or a set
	 * result type.
	 *
	 * @author dereekb
	 */
	protected abstract class SimpleOneByOneAbstractInstance extends OneByOneAbstractInstance<RelatedModelUpdaterResult, Boolean> {

		// MARK: Overrides
		@Override
		protected final boolean performChangesForRelation(T model,
		                                                  R relation,
		                                                  Boolean changes) {
			return this.performChangesForRelation(model, relation);
		}

		@Override
		protected final Boolean makeInitialChangesModel() {
			return false;
		}

		@Override
		protected final void saveChanges(T model,
		                                 List<R> updated,
		                                 Boolean changes) {
			this.saveChanges(model, updated);
		}

		@Override
		protected final Boolean mergePartitionChanges(List<Boolean> changes) {
			return null;	// Unused
		}

		@Override
		protected final RelatedModelUpdaterResult makeOutputForChanges(List<Boolean> changes) {
			return this.makeOutputForChanges();
		}

		// MARK: Abstract
		protected abstract boolean performChangesForRelation(T model,
		                                                     R relation);

		protected abstract void saveChanges(T model,
		                                    List<R> updated);

		protected RelatedModelUpdaterResult makeOutputForChanges() {
			return null;	// Unused. No output changes by default.
		}

	}

	/**
	 * {@link AbstractInstance} extension that iterates over each of the models
	 * one-by-one.
	 *
	 * @author dereekb
	 *
	 * @param <O>
	 *            result type
	 * @param <C>
	 *            changes
	 */
	protected abstract class OneByOneAbstractInstance<O extends RelatedModelUpdaterResult, C> extends AbstractInstance<O, C> {

		@Override
		protected C performChangesForRelationPartition(RelationChangesInput<T, R> input) {
			C changes = this.makeInitialChangesModel();

			T model = input.getInputModel();

			List<R> relations = input.getRelationModels();

			List<R> updated = new ArrayList<R>();

			for (R relation : relations) {
				boolean wasChanged = this.performChangesForRelation(model, relation, changes);

				if (wasChanged) {
					updated.add(relation);
				}
			}

			this.saveChanges(model, updated, changes);

			return changes;
		}

		/**
		 * Performs the changes for the input relation, and updates the input
		 * changes model if necessary.
		 *
		 * @param model
		 *            Input model. Never {@code null}.
		 * @param relation
		 *            Relation model. Never {@code null}.
		 * @param changes
		 *            Changes model. Never {@code null}.
		 *
		 * @return {@code true} if changes were performed on the model.
		 */
		protected abstract boolean performChangesForRelation(T model,
		                                                     R relation,
		                                                     C changes);

		/**
		 * Makes a new changes model.
		 *
		 * @return Changes model. Never {@code null}.
		 */
		protected abstract C makeInitialChangesModel();

		/**
		 * Saves the updated models.
		 *
		 * @param model
		 *            Input model. Never {@code null}.
		 * @param updated
		 *            {@link List}. Never {@code null}.
		 * @param changes
		 *            Changes model. Never {@code null}.
		 */
		protected abstract void saveChanges(T model,
		                                    List<R> updated,
		                                    C changes);

	}

	protected abstract class AbstractInstance<O extends RelatedModelUpdaterResult, C>
	        implements Instance<T> {

		protected final Partitioner PARTITIONER;

		public AbstractInstance() {
			this(10);
		}

		/**
		 * Sets the partition size for batches.
		 *
		 * This batch size limit is related to the Google App Engine's
		 * transactional limit of 25 entities/transaction.
		 */
		public AbstractInstance(int partitionSize) {
			this.PARTITIONER = new PartitionerImpl(partitionSize);
		}

		// MARK: Instance
		@Override
		public final O performChanges(Iterable<T> models) {
			List<C> changes = new ArrayList<C>();

			try {
				HashMapWithList<T, N> intermediaryMap = this.loadIntermediaries(models);

				// Update each model independently.
				for (Entry<T, List<N>> entry : intermediaryMap.entrySet()) {
					C change = this.performSafeChangesForRelation(entry.getKey(), entry.getValue());
					changes.add(change);
				}
			} catch (NoChangesAvailableException e) {
				// Do nothing.
			}

			O output = this.makeOutputForChanges(changes);
			return output;
		}

		/**
		 * Loads all intermediary models for the input.
		 * <p>
		 * This is performed outside of a transaction, allowing the use of
		 * queries.
		 *
		 * @param models
		 *            {@link Iterable}. Never {@code null}.
		 * @return {@link List} of models. Never {@code null}.
		 * @throws NoChangesAvailableException
		 *             thrown if there are no models available and no
		 *             changes should occur. In some cases the implementation
		 *             will prefer to attempt changes anyways without
		 *             intermediary models, in which case this exception would
		 *             not be thrown.
		 */
		protected abstract HashMapWithList<T, N> loadIntermediaries(Iterable<T> models)
		        throws NoChangesAvailableException;

		private C performSafeChangesForRelation(final T model,
		                                        final List<N> intermediaryModels) {
			final Collection<ModelKey> relatedModelKeys = this.getRelatedModelKeys(model, intermediaryModels);

			List<C> changes = new ArrayList<C>();
			List<List<ModelKey>> partitions = this.PARTITIONER.makePartitions(relatedModelKeys);

			for (List<ModelKey> partition : partitions) {
				C change = this.performSafeChangesForRelationPartition(model, partition);
				ListUtility.addElement(changes, change);
			}

			return this.mergePartitionChanges(changes);
		}

		/**
		 * Returns a collection of expected related models.
		 * <p>
		 * This is performed outside of a transaction.
		 *
		 * @param model
		 *            Model. Never {@code null}.
		 * @param intermediaryModels
		 *            {@link List}. Never {@code null}.
		 * @return {@link Collection}. Never {@code null}.
		 */
		protected abstract Collection<ModelKey> getRelatedModelKeys(T model,
		                                                            List<N> intermediaryModels);

		/**
		 * Performs the safe changes.
		 */
		private C performSafeChangesForRelationPartition(final T model,
		                                                 final List<ModelKey> relatedModelKeys) {

			return ObjectifyService.ofy().transactNew(new Work<C>() {

				@Override
				public C run() {

					// Read the model again for transaction safety.
					T inputModel = AbstractOneToIntermediaryToManyRelationUpdater.this.inputModelGetter.get(model);

					boolean modelExists = inputModel != null;

					// Re-read the relation key from the transaction-safe model.
					// The model may have been deleted, in which case we use the
					// original value from above.
					if (!modelExists) {
						inputModel = model;
					}

					if (AbstractInstance.this.shouldPerformChangesWithModel(inputModel, modelExists)) {

						// Load all available related types.
						List<R> relatedModels = AbstractOneToIntermediaryToManyRelationUpdater.this.relationModelGetter
						        .getWithKeys(relatedModelKeys);

						RelationChangesInputImpl input = new RelationChangesInputImpl(inputModel, relatedModelKeys,
						        relatedModels);
						return AbstractInstance.this.performChangesForRelationPartition(input);
					} else {
						return AbstractInstance.this.makeNoChangesResult(model, modelExists);
					}
				}

			});
		}

		protected abstract C mergePartitionChanges(List<C> changes);

		protected boolean shouldPerformChangesWithModel(T model,
		                                                boolean modelExists) {
			return modelExists;
		}

		/**
		 * Creates a result when no changes occur.
		 *
		 * @param model
		 *            Input model.
		 * @return Result. Can be {@code null}.
		 */
		protected C makeNoChangesResult(T model,
		                                boolean modelExists) {
			return null;
		}

		/**
		 * Performs the updates to the input model and all available related
		 * models within a new transaction.
		 * <p>
		 * Single the input model does not know about the related types per the
		 * design, only the related types should be modified.
		 *
		 * @param input
		 *            {@link RelationChangesInput}. Never {@code null}.
		 * @return Changes. Never {@code null}.
		 */
		protected abstract C performChangesForRelationPartition(RelationChangesInput<T, R> input);

		private class RelationChangesInputImpl
		        implements RelationChangesInput<T, R> {

			private final T inputModel;

			private final List<ModelKey> relationModelKeys;
			private final List<R> relationModels;

			public RelationChangesInputImpl(T inputModel, List<ModelKey> relationModelKeys, List<R> relationModels) {
				this.relationModelKeys = relationModelKeys;
				this.inputModel = inputModel;
				this.relationModels = relationModels;
			}

			@Override
			public ModelKey getInputModelKey() {
				return this.inputModel.getModelKey();
			}

			@Override
			public List<ModelKey> getRelationModelKeys() {
				return this.relationModelKeys;
			}

			@Override
			public T getInputModel() {
				return this.inputModel;
			}

			@Override
			public List<R> getRelationModels() {
				return this.relationModels;
			}

		}

		// MARK: Internal - Results
		/**
		 * Merges all the change data together before returning.
		 */
		protected abstract O makeOutputForChanges(List<C> changes);

	}

	/**
	 * Thrown when no changes should be made.
	 *
	 * @author dereekb
	 */
	protected static class NoChangesAvailableException extends Exception {

		private static final long serialVersionUID = 1L;

	}

	protected interface RelationChangesInput<T extends UniqueModel, R extends UniqueModel> {

		public ModelKey getInputModelKey();

		public T getInputModel();

		public List<ModelKey> getRelationModelKeys();

		public List<R> getRelationModels();

	}

}
