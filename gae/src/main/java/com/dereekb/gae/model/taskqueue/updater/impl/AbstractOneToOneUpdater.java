package com.dereekb.gae.model.taskqueue.updater.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdater;
import com.dereekb.gae.model.taskqueue.updater.RelatedModelUpdaterResult;
import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
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
	protected abstract class SimpleAbstractInstance extends AbstractInstance<ExtendedAbstractInstanceResults, ExtendedAbstractInstanceChange> {

		@Override
		protected ExtendedAbstractInstanceChange performChangesForRelation(RelationChangesInput<T, R> input) {
			switch (input.getState()) {
				case NO_RELATION:
					this.tryCreateRelation(input);
					break;
				case NO_INPUT:
					this.deleteRelation(input);
					break;
				case BOTH:
					// Do Both.
					this.updateRelation(input.getInputModel(), input.getRelationModel());
					break;
				case NONE:
					// Do nothing.
					this.doNothing();
					break;
			}

			return new ExtendedAbstractInstanceChange();
		}

		// MARK: Changes
		protected void tryCreateRelation(RelationChangesInput<T, R> input) {
			if (this.shouldHaveRelation(input.getInputModel())) {
				this.createNewRelation(input);
			}
		}

		protected abstract boolean shouldHaveRelation(T input);

		protected abstract void createNewRelation(RelationChangesInput<T, R> input);

		protected abstract void deleteRelation(RelationChangesInput<T, R> input);

		protected abstract void updateRelation(T inputModel,
		                                       R relationModel);

		protected void doNothing() {}

		// MARK: Internal - Results
		@Override
		protected ExtendedAbstractInstanceResults makeOutputForChanges(List<ExtendedAbstractInstanceChange> changes) {
			// TODO: Create results here...

			return null;
		}

	}

	protected class ExtendedAbstractInstanceResults
	        implements RelatedModelUpdaterResult {

	}

	protected class ExtendedAbstractInstanceChange {

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
	protected abstract class AbstractInstance<O extends RelatedModelUpdaterResult, C>
	        implements Instance<T> {

		// MARK: Instance
		@Override
		public final O performChanges(Iterable<T> models) {
			Map<ModelKey, T> map = this.buildRelationMap(models);
			List<C> changes = new ArrayList<C>();

			// Perform changes for each relation independently.
			for (Entry<ModelKey, T> entry : map.entrySet()) {
				C change = this.performSafeChangesForRelation(entry.getKey(), entry.getValue());
				changes.add(change);
			}

			return this.makeOutputForChanges(changes);
		}

		// MARK: Internal - Relation Map
		private Map<ModelKey, T> buildRelationMap(Iterable<T> models) {
			Map<ModelKey, T> map = new HashMap<ModelKey, T>();

			for (T model : models) {
				ModelKey relationModelKey = this.getRelationModelKey(model);
				map.put(relationModelKey, model);
			}

			return map;
		}

		protected abstract ModelKey getRelationModelKey(T model);

		// MARK: Internal - Relation Changes
		private C performSafeChangesForRelation(final ModelKey relationModelKey,
		                                        final T value) {
			final ModelKey inputModelKey = value.getModelKey();

			return ObjectifyService.ofy().transactNew(new Work<C>() {

				@Override
				public C run() {

					// Attempts to load the models.
					R relationModel = AbstractOneToOneUpdater.this.relationModelGetter.get(relationModelKey);
					T inputModel = AbstractOneToOneUpdater.this.inputModelGetter.get(value);

					RelationChangesInputImpl input = new RelationChangesInputImpl(inputModelKey, relationModelKey,
					        inputModel, relationModel);
					return AbstractInstance.this.performChangesForRelation(input);
				}

			});
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

		NO_INPUT,
		NO_RELATION,
		NONE,
		BOTH

	}

}