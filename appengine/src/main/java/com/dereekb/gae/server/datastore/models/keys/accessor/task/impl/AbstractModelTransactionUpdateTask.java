package com.dereekb.gae.server.datastore.models.keys.accessor.task.impl;

import java.util.List;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.utility.StagedTransactionChange;
import com.dereekb.gae.server.datastore.utility.impl.StagedTransactionChangeCollection;

/**
 * Abstract {@link AbstractModelStagedTransactionChangeTask} that loads models
 * using a {@link Getter} and iterates over models individually.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractModelTransactionUpdateTask<T extends UniqueModel> extends AbstractModelStagedTransactionChangeTask<T> {

	private Getter<T> getter;

	public AbstractModelTransactionUpdateTask(Getter<T> getter) {
		this(null, getter);
	}

	public AbstractModelTransactionUpdateTask(Integer partitionSize, Getter<T> getter) {
		super(partitionSize);
		this.setGetter(getter);
	}

	public Getter<T> getGetter() {
		return this.getter;
	}

	public void setGetter(Getter<T> getter) {
		if (getter == null) {
			throw new IllegalArgumentException("getter cannot be null.");
		}

		this.getter = getter;
	}

	// MARK: Work
	protected abstract class AbstractModelTransactionUpdaterWork extends AbstractTransactionWork {

		public AbstractModelTransactionUpdaterWork(Iterable<ModelKey> input) {
			super(input);
		}

		// MARK: Work
		@Override
		public final StagedTransactionChangeCollection run() {
			this.initForRun();
			List<T> models = AbstractModelTransactionUpdateTask.this.getter.getWithKeys(this.input);
			StagedTransactionChangeCollection changes = this.updateModels(models);

			if (changes == null) {
				changes = StagedTransactionChangeCollection.make();	// Always
				                                                   	// return
				                                                   	// changes.
			}

			return changes;
		}

		protected void initForRun() {};

		protected StagedTransactionChangeCollection updateModels(List<T> models) {
			StagedTransactionChangeCollection changes = StagedTransactionChangeCollection.make();

			for (T model : models) {
				StagedTransactionChange change = this.updateModel(model);
				changes.addChange(change);
			}

			return changes;
		}

		protected abstract StagedTransactionChange updateModel(T model);

	}

}
