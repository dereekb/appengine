package com.dereekb.gae.server.datastore.models.keys.accessor.task.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.StagedUpdater;
import com.dereekb.gae.server.datastore.utility.StagedUpdaterFactory;
import com.dereekb.gae.server.datastore.utility.impl.StagedTransactionChangeCollection;
import com.dereekb.gae.utilities.task.exception.NoOpException;

/**
 * Abstract {@link AbstractModelTransactionUpdateTask} extension that edits the
 * models then saves the change.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractSimpleModelTransactionUpdateTask<T extends UniqueModel> extends AbstractModelTransactionUpdateTask<T> {

	private StagedUpdaterFactory<T> updater;

	public AbstractSimpleModelTransactionUpdateTask(Getter<T> getter, StagedUpdaterFactory<T> updater) {
		super(getter);
		this.setUpdater(updater);
	}

	public StagedUpdaterFactory<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(StagedUpdaterFactory<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}

		this.updater = updater;
	}

	// MARK: Work
	protected abstract class AbstractSimpleSingleUpdaterWork extends AbstractModelTransactionUpdaterWork {

		private StagedUpdater<T> updaterInstance = AbstractSimpleModelTransactionUpdateTask.this.updater.makeUpdater();

		public AbstractSimpleSingleUpdaterWork(Iterable<ModelKey> input) {
			super(input);
		}

		// MARK: Work
		@Override
		protected final StagedTransactionChangeCollection updateModels(List<T> models) {
			StagedTransactionChangeCollection result = super.updateModels(models);

			// Add the updater instance itself to the results.
			result.addChange(this.updaterInstance);
			return result;
		}

		@Override
		protected final StagedTransactionChange updateModel(T model) {
			try {
				StagedTransactionChange change = this.simpleUpdateModel(model);
				this.updaterInstance.updateAsync(model);
				return change;
			} catch (NoOpException e) {
				return null;
			}
		}

		/**
		 * Updates the input model.
		 *
		 * @param model
		 *            model. Never {@code null}.
		 * @return {@link StagedTransactionChange}, or {@code null} if not
		 *         applicable.
		 * @throws NoOpException
		 *             thrown if no change occurs and no save is required.
		 */
		protected abstract StagedTransactionChange simpleUpdateModel(T model) throws NoOpException;

	}

}
