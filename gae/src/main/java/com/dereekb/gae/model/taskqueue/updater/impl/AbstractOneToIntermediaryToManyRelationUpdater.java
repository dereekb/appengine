package com.dereekb.gae.model.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
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

	private Getter<R> relatedModelGetter;

	public AbstractOneToIntermediaryToManyRelationUpdater(Getter<R> relatedModelGetter) {
		this.setRelatedModelGetter(relatedModelGetter);
	}

	public Getter<R> getRelatedModelGetter() {
		return this.relatedModelGetter;
	}

	public void setRelatedModelGetter(Getter<R> relatedModelGetter) {
		if (relatedModelGetter == null) {
			throw new IllegalArgumentException("relatedModelGetter cannot be null.");
		}

		this.relatedModelGetter = relatedModelGetter;
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

			return this.makeOutputForChanges(changes);
		}

		/**
		 * Loads all intermediary models for the input.
		 * <p>
		 * This is performed outside of a transaction.
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
				this.performSafeChangesForRelationPartition(model, partition);
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

		private C performSafeChangesForRelationPartition(final T model,
		                                                 final List<ModelKey> relatedModelKeys) {
			return ObjectifyService.ofy().transactNew(new Work<C>() {

				@Override
				public C run() {
					// Load all available related types.
					List<R> relatedModels = AbstractOneToIntermediaryToManyRelationUpdater.this.relatedModelGetter
					        .getWithKeys(relatedModelKeys);

					RelationChangesInputImpl input = new RelationChangesInputImpl(model, relatedModelKeys,
					        relatedModels);
					return AbstractInstance.this.performChangesForRelationPartition(input);
				}

			});
		}

		protected abstract C mergePartitionChanges(List<C> changes);

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
