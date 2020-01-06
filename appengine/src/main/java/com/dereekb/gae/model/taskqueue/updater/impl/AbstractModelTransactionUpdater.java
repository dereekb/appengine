package com.dereekb.gae.model.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelTransactionUpdateTask;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;
import com.dereekb.gae.server.datastore.utility.StagedUpdaterFactory;
import com.dereekb.gae.server.datastore.utility.impl.StagedTransactionChangeCollection;
import com.dereekb.gae.utilities.collections.batch.Partitioner;
import com.dereekb.gae.utilities.collections.batch.impl.PartitionerImpl;
import com.dereekb.gae.utilities.collections.list.ListUtility;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * {@link AbstractUpdater} implementation that updates the input models within a
 * transaction. Models are modified in batches.
 * <p>
 * This should typically only be used in cases where attributes on the model
 * being updated.
 *
 * @author dereekb
 *
 * @see {@link AbstractModelTransactionUpdateTask} as an alternative
 *      implementation with different design goals.
 */
public abstract class AbstractModelTransactionUpdater<T extends UniqueModel> extends AbstractUpdater<T> {

	/**
	 * Default size of the batches of models to update.
	 */
	public static final int DEFAULT_INSTANCE_BATCH_SIZE = 20;

	private Getter<T> modelGetter;

	/**
	 * Optional updater. Should be provided if {@link #stagedUpdaterFactory} is
	 * not.
	 */
	private Updater<T> modelUpdater;

	/**
	 * Optional updater factory.
	 * <p>
	 * If not provided, the instance will be in-charge of saving models.
	 */
	private StagedUpdaterFactory<T> stagedUpdaterFactory;

	private int instanceBatchSize = DEFAULT_INSTANCE_BATCH_SIZE;

	protected AbstractModelTransactionUpdater(Getter<T> modelGetter, Updater<T> updater) {
		super();
		this.setModelGetter(modelGetter);
		this.setModelUpdater(updater);
	}

	protected AbstractModelTransactionUpdater(Getter<T> modelGetter, StagedUpdaterFactory<T> stagedUpdaterFactory) {
		super();
		this.setModelGetter(modelGetter);
		this.setStagedUpdaterFactory(stagedUpdaterFactory);
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

	public Updater<T> getModelUpdater() {
		return this.modelUpdater;
	}

	public void setModelUpdater(Updater<T> modelUpdater) {
		if (modelUpdater == null) {
			throw new IllegalArgumentException("ModelUpdater cannot be null.");
		} else {
			this.setStagedUpdaterFactory(null);	// Clear the staged updater.
		}

		this.modelUpdater = modelUpdater;
	}

	public StagedUpdaterFactory<T> getStagedUpdaterFactory() {
		return this.stagedUpdaterFactory;
	}

	public void setStagedUpdaterFactory(StagedUpdaterFactory<T> stagedUpdaterFactory) {
		if (stagedUpdaterFactory != null) {
			this.modelUpdater = null;	// Clear the model updater.
		}

		this.stagedUpdaterFactory = stagedUpdaterFactory;
	}

	public int getInstanceBatchSize() {
		return this.instanceBatchSize;
	}

	public void setInstanceBatchSize(int instanceBatchSize) {
		this.instanceBatchSize = instanceBatchSize;
	}

	// MARK: Instances
	protected abstract class SimpleAbstractInstance extends ChangesOnlyAbstractInstance {

		@Override
		protected final HandlerPair<Boolean, StagedTransactionChange> performChangesOnModel(T input) {
			boolean modified = this.performChangeOnModel(input);
			return new HandlerPair<Boolean, StagedTransactionChange>(modified, null);
		}

		/**
		 * Performs changes on the input model.
		 *
		 * @param input
		 *            Model. Never {@code null}.
		 * @return {@code false} if the model did not change.
		 */
		protected abstract boolean performChangeOnModel(T input);

	}

	protected abstract class ChangesOnlyAbstractInstance extends AbstractInstance<RelatedModelUpdaterResult, StagedTransactionChange> {

		@Override
		protected RelatedModelUpdaterResult makeOutputForChanges(List<StagedTransactionChange> changes,
		                                                         StagedTransactionChange updaterChange) {

			StagedTransactionChangeCollection changesCollection = new StagedTransactionChangeCollection();
			changesCollection.addAllChange(changes);
			changesCollection.addChange(updaterChange);

			return new RelatedModelUpdaterResultImpl(changesCollection);
		}

	}

	/**
	 * Basic abstract instance.
	 *
	 * @author dereekb
	 *
	 * @param <O>
	 *            output type
	 * @param <C>
	 *            changes result type
	 */
	protected abstract class AbstractInstance<O extends RelatedModelUpdaterResult, C extends StagedTransactionChange>
	        implements Instance<T> {

		protected final Partitioner PARTITIONER = new PartitionerImpl(
		        AbstractModelTransactionUpdater.this.instanceBatchSize);

		private final StagedUpdater<T> stagedUpdater;
		private final Updater<T> updater;

		protected AbstractInstance() {
			if (AbstractModelTransactionUpdater.this.stagedUpdaterFactory != null) {
				this.stagedUpdater = AbstractModelTransactionUpdater.this.stagedUpdaterFactory.makeUpdater();
				this.updater = this.stagedUpdater;
			} else {
				this.stagedUpdater = null;
				this.updater = AbstractModelTransactionUpdater.this.modelUpdater;
			}
		}

		// MARK: Instance
		@Override
		public final O performChanges(Iterable<T> models) {
			List<List<T>> partitions = this.PARTITIONER.makePartitions(models);
			List<C> changes = new ArrayList<C>();

			// Perform changes for each relation independently.
			for (List<T> partition : partitions) {
				List<C> partitionChanges = this.performSafeChanges(partition);
				changes.addAll(partitionChanges);
			}

			return this.makeOutputForChanges(changes, this.stagedUpdater);
		}

		// MARK: Internal - Model Changes
		private final List<C> performSafeChanges(final List<T> values) {

			return ObjectifyService.ofy().transactNew(new Work<List<C>>() {

				@Override
				public List<C> run() {

					// Read the models again for transaction safety.
					List<T> inputModels = AbstractModelTransactionUpdater.this.modelGetter.get(values);

					HandlerPair<List<T>, List<C>> results = AbstractInstance.this.performChangesOnModels(inputModels);
					List<T> updatedModels = results.getKey();
					List<C> stagedChanges = results.getObject();

					if (!updatedModels.isEmpty()) {
						AbstractInstance.this.updater.update(updatedModels);
					}

					return stagedChanges;
				}

			});
		}

		/**
		 * Attempts to perform the changes on models. Returns the list of models
		 * that were modified.
		 *
		 * @param input
		 * @return {@link List}. Never {@code null}.
		 */
		protected HandlerPair<List<T>, List<C>> performChangesOnModels(List<T> inputModels) {
			List<T> updatedModels = new ArrayList<T>();
			List<C> stagedChanges = new ArrayList<C>();

			for (T model : inputModels) {
				HandlerPair<Boolean, C> result = this.performChangesOnModel(model);
				boolean updated = result.getKey();

				if (updated) {
					updatedModels.add(model);
					ListUtility.addElement(stagedChanges, result.getObject());
				}
			}

			return new HandlerPair<List<T>, List<C>>(updatedModels, stagedChanges);
		}

		protected HandlerPair<Boolean, C> performChangesOnModel(T input) {
			throw new UnsupportedOperationException("Override in subclass to use.");
		}

		// MARK: Internal - Results
		protected abstract O makeOutputForChanges(List<C> changes,
		                                          StagedTransactionChange updaterChange);

	}

}
