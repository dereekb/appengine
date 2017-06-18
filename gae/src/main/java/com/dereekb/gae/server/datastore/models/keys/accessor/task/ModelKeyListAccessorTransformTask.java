package com.dereekb.gae.server.datastore.models.keys.accessor.task;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link ModelKeyListAccessorTask} that takes in one
 * {@link ModelKeyListAccessor}, converts it, then uses it as input for the next
 * {@link ModelKeyListAccessorTask}.
 * 
 * @author dereekb
 *
 * @param <I>
 *            input type
 * @param <O>
 *            output type
 */
public class ModelKeyListAccessorTransformTask<I extends UniqueModel, O extends UniqueModel>
        implements ModelKeyListAccessorTask<I> {

	private Task<ModelKeyListAccessor<O>> task;
	private ModelKeyListAccessorTransformTaskDelegate<I, O> delegate;

	public ModelKeyListAccessorTransformTask(Task<ModelKeyListAccessor<O>> task,
	        ModelKeyListAccessorTransformTaskDelegate<I, O> delegate) {
		this.setTask(task);
		this.setDelegate(delegate);
	}

	public Task<ModelKeyListAccessor<O>> getTask() {
		return this.task;
	}

	public void setTask(Task<ModelKeyListAccessor<O>> task) {
		if (task == null) {
			throw new IllegalArgumentException("task cannot be null.");
		}

		this.task = task;
	}

	public ModelKeyListAccessorTransformTaskDelegate<I, O> getDelegate() {
		return this.delegate;
	}

	public void setDelegate(ModelKeyListAccessorTransformTaskDelegate<I, O> delegate) {
		if (delegate == null) {
			throw new IllegalArgumentException("delegate cannot be null.");
		}

		this.delegate = delegate;
	}

	// MARK: ModelKeyListAccessorTask
	@Override
	public void doTask(ModelKeyListAccessor<I> input) throws FailedTaskException {
		ModelKeyListAccessor<O> outputAccessor = this.delegate.convertListAccessor(input);
		this.task.doTask(outputAccessor);
	}

	@Override
	public String toString() {
		return "ModelKeyListAccessorTransformTask [task=" + this.task + ", delegate=" + this.delegate + "]";
	}

}
