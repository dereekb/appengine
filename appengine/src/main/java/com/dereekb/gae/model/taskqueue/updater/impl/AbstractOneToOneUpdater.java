package com.dereekb.gae.model.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.impl.StagedTransactionChangeCollection;
import com.dereekb.gae.utilities.collections.pairs.impl.HandlerPair;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Work;

/**
 * {@link RelatedModelUpdater} implementation used for a one-to-one relations.
 * <p>
 * Modified elements are reloaded before attempting to be modified.
 * <p>
 * Relation objects that don't yet exist may be created through this mechanism,
 * and ones that should no longer exist may be deleted.
 *
 * @author dereekb
 *
 * @param <T>
 *            input model type
 * @param <R>
 *            relation model type
 */
public abstract class AbstractOneToOneUpdater<T extends UniqueModel, R extends UniqueModel> extends AbstractUpdater<T> {

	private Getter<T> inputModelGetter;
	private Getter<R> relationModelGetter;

	public AbstractOneToOneUpdater(Getter<T> inputModelGetter, Getter<R> relationModelGetter) {
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
	 * {@link AbstractInstance} extension that makes some assumptions about how
	 * the relation models should be modified when one or the other are missing.
	 * <p>
	 * This extension assumes that if the input model does not exist, the
	 * relation model should not exist either. The relation object is optional,
	 * but will be checked each time to see if it should exist or not, if it
	 * does not exist.
	 * <p>
	 * With this setup, generally the instance has
	 *
	 * @author dereekb
	 *
	 * @param <O>
	 *            output type
	 * @param <C>
	 *            changes result type
	 */
	protected abstract class SimpleAbstractInstance extends AbstractInstance<RelatedModelUpdaterResultImpl, StagedTransactionChange> {

		@Override
		protected StagedTransactionChange performChangesForRelation(RelationChangesInput<T, R> input) {
			StagedTransactionChange transactionChange = null;

			switch (input.getState()) {
				case NO_RELATION:
					transactionChange = this.tryCreateRelation(input);
					break;
				case NO_INPUT:
					transactionChange = this.deleteRelation(input);
					break;
				case BOTH:
					// Do Both.
					transactionChange = this.updateRelation(input.getInputModel(), input.getRelationModel());
					break;
				case NONE:
					// Do nothing.
					this.doNothing();
					break;
			}

			return transactionChange;
		}

		// MARK: Changes
		protected StagedTransactionChange tryCreateRelation(RelationChangesInput<T, R> input) {
			if (this.shouldHaveRelation(input.getInputModel())) {
				return this.createNewRelation(input);
			} else {
				return null;
			}
		}

		protected abstract boolean shouldHaveRelation(T input);

		protected StagedTransactionChange createNewRelation(RelationChangesInput<T, R> input) {
			// Do nothing by default
			return null;
		}

		protected StagedTransactionChange deleteRelation(RelationChangesInput<T, R> input) {
			R relationModel = input.getRelationModel();

			if (relationModel != null) {
				// Call other input by default.
				return this.deleteRelation(input.getInputModel(), relationModel);
			} else {
				return null;
			}
		}

		protected StagedTransactionChange deleteRelation(T inputModel,
		                                                 R relationModel) {
			// Do nothing by default
			return null;
		}

		protected StagedTransactionChange updateRelation(T inputModel,
		                                                 R relationModel) {
			// Do nothing by default
			return null;
		}

		protected void doNothing() {}

		// MARK: Internal - Results
		@Override
		protected RelatedModelUpdaterResultImpl makeOutputForChanges(List<StagedTransactionChange> changes) {
			StagedTransactionChangeCollection changeCollection = new StagedTransactionChangeCollection();

			for (StagedTransactionChange change : changes) {
				changeCollection.addChange(change);
			}

			changeCollection.addChange(this.includeAdditionalChanges());

			return new RelatedModelUpdaterResultImpl(changeCollection);
		}

		protected StagedTransactionChange includeAdditionalChanges() {
			return null;
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

		// MARK: Instance
		@Override
		public final O performChanges(Iterable<T> models) {
			List<HandlerPair<ModelKey, T>> pairs = this.buildRelationPairs(models);
			List<C> changes = new ArrayList<C>();

			// Perform changes for each relation independently.
			for (HandlerPair<ModelKey, T> pair : pairs) {
				C change = this.performSafeChangesForRelation(pair.getKey(), pair.getObject());

				if (change != null) {
					changes.add(change);
				}
			}

			return this.makeOutputForChanges(changes);
		}

		// MARK: Internal - Relation Map
		private final List<HandlerPair<ModelKey, T>> buildRelationPairs(Iterable<T> models) {
			List<HandlerPair<ModelKey, T>> pairs = new ArrayList<HandlerPair<ModelKey, T>>();

			for (T model : models) {
				ModelKey relationModelKey = this.getRelationModelKey(model);
				pairs.add(new HandlerPair<ModelKey, T>(relationModelKey, model));
			}

			return pairs;
		}

		protected abstract ModelKey getRelationModelKey(T model);

		// MARK: Internal - Relation Changes
		private final C performSafeChangesForRelation(final ModelKey inputRelationModelKey,
		                                              final T model) {
			final ModelKey inputModelKey = model.getModelKey();

			return ObjectifyService.ofy().transactNew(new Work<C>() {

				@Override
				public C run() {
					ModelKey relationModelKey = inputRelationModelKey;

					// Read the model again for transaction safety.
					T inputModel = AbstractOneToOneUpdater.this.inputModelGetter.get(model);

					boolean modelExists = inputModel != null;

					// Re-read the relation key from the transaction-safe model.
					// The model may have been deleted, in which case we use the
					// original value from above.
					if (!modelExists) {
						inputModel = model;
					}

					// Re-read the relation key from the transaction-safe model.
					// The model may have been deleted, in which case we use the
					// original value from above.
					if (inputModel != null) {
						relationModelKey = getRelationModelKey(inputModel);
					}

					if (AbstractInstance.this.shouldPerformChangesWithModel(inputModel, modelExists)) {

						// Attempts to load the models.
						R relationModel = (relationModelKey != null)
						        ? AbstractOneToOneUpdater.this.relationModelGetter.get(relationModelKey)
						        : null;

						RelationChangesInputImpl input = new RelationChangesInputImpl(inputModelKey, relationModelKey,
						        inputModel, relationModel);
						return AbstractInstance.this.performChangesForRelation(input);
					} else {
						return AbstractInstance.this.makeNoChangesResult(model, modelExists);
					}
				}

			});
		}

		protected boolean shouldPerformChangesWithModel(T model,
		                                                boolean modelExists) {
			return true;
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

		private class RelationChangesInputImpl
		        implements RelationChangesInput<T, R> {

			private final ModelKey inputModelKey;
			private final ModelKey relationModelKey;
			private final T inputModel;
			private final R relationModel;

			private final RelationChangesInputState state;

			public RelationChangesInputImpl(ModelKey inputModelKey,
			        ModelKey relationModelKey,
			        T inputModel,
			        R relationModel) {
				this.inputModelKey = inputModelKey;
				this.relationModelKey = relationModelKey;
				this.inputModel = inputModel;
				this.relationModel = relationModel;

				// Compute the state
				RelationChangesInputState state = null;

				if (inputModel == null) {
					if (relationModel == null) {
						state = RelationChangesInputState.NONE;
					} else {
						state = RelationChangesInputState.NO_INPUT;
					}
				} else if (relationModel == null) {
					state = RelationChangesInputState.NO_RELATION;
				} else {
					state = RelationChangesInputState.BOTH;
				}

				this.state = state;
			}

			@Override
			public ModelKey getInputModelKey() {
				return this.inputModelKey;
			}

			@Override
			public ModelKey getRelationModelKey() {
				return this.relationModelKey;
			}

			@Override
			public T getInputModel() {
				return this.inputModel;
			}

			@Override
			public R getRelationModel() {
				return this.relationModel;
			}

			@Override
			public RelationChangesInputState getState() {
				return this.state;
			}
		}

		protected abstract C performChangesForRelation(RelationChangesInput<T, R> input);

		// MARK: Internal - Results
		protected abstract O makeOutputForChanges(List<C> changes);

	}

	protected interface RelationChangesInput<T extends UniqueModel, R extends UniqueModel> {

		public R getRelationModel();

		public T getInputModel();

		public ModelKey getRelationModelKey();

		public ModelKey getInputModelKey();

		public RelationChangesInputState getState();

	}

	protected enum RelationChangesInputState {

		/**
		 * When neither the input model nor the relation model exists.
		 */
		NONE,

		/**
		 * When the relation model exists, but the input model does not.
		 * <p>
		 * This occurs when the input model was deleted.
		 */
		NO_INPUT,

		/**
		 * When the input model exists but the related model does not.
		 */
		NO_RELATION,

		/**
		 * When both the input model and the related model exists.
		 */
		BOTH

	}

}
