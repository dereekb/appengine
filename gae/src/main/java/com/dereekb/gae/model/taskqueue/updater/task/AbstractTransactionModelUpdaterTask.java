package com.dereekb.gae.model.taskqueue.updater.task;

import java.util.ArrayList;
import java.util.List;

import com.dereekb.gae.server.datastore.Getter;
import com.dereekb.gae.server.datastore.Updater;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.server.datastore.models.keys.accessor.task.impl.AbstractModelKeyListAccessorTask;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility;
import com.dereekb.gae.server.datastore.objectify.helpers.ObjectifyTransactionUtility.PartitionDelegate;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;

/**
 * Abstract {@link AbstractModelKeyListAccessorTask} implementation that reads
 * and modifies the target models using a delegate, objectify/google app engine
 * transactions, and an {@link Updater}.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public abstract class AbstractTransactionModelUpdaterTask<T extends UniqueModel> extends AbstractModelKeyListAccessorTask<T> {

	private Getter<T> getter;
	private Updater<T> updater;

	public AbstractTransactionModelUpdaterTask(Getter<T> getter, Updater<T> updater) {
		super();
		this.setGetter(getter);
		this.setUpdater(updater);
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

	public Updater<T> getUpdater() {
		return this.updater;
	}

	public void setUpdater(Updater<T> updater) {
		if (updater == null) {
			throw new IllegalArgumentException("updater cannot be null.");
		}

		this.updater = updater;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		final PartitionDelegate<ModelKey, ?> delegate = this.makeUpdatePartitionDelegate();

		try {
			ObjectifyTransactionUtility.doPartitionedTransactNew(input, delegate);
		} catch (RuntimeException e) {
			throw new FailedTaskException(e);
		}
	}

	protected abstract PartitionDelegate<ModelKey, ?> makeUpdatePartitionDelegate();

	protected abstract class PartitionDelegateImpl
	        implements PartitionDelegate<ModelKey, Void> {

		@Override
		public Work<Void> makeWorkForInput(final Iterable<ModelKey> input) {
			return new VoidWork() {

				@Override
				public void vrun() {
					List<T> models = AbstractTransactionModelUpdaterTask.this.getModels(input);
					List<T> updated = new ArrayList<T>();

					for (T model : models) {
						if (PartitionDelegateImpl.this.updateModel(model)) {
							updated.add(model);
						}
					}

					if (updated.isEmpty() == false) {
						AbstractTransactionModelUpdaterTask.this.updateModels(updated);
					}
				}

			};
		}

		protected abstract boolean updateModel(T model);

	}

	protected List<T> getModels(Iterable<ModelKey> keys) {
		return AbstractTransactionModelUpdaterTask.this.getter.getWithKeys(keys);
	}

	protected void updateModels(Iterable<T> models) {
		AbstractTransactionModelUpdaterTask.this.updater.update(models);
	}

}
