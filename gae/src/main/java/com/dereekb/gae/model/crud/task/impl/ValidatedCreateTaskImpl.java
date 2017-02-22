package com.dereekb.gae.model.crud.task.impl;

import com.dereekb.gae.model.crud.pairs.CreatePair;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.task.config.CreateTaskConfig;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskDelegate;
import com.dereekb.gae.model.crud.task.impl.delegate.CreateTaskValidator;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.taskqueue.scheduler.utility.builder.TaskRequestSender;
import com.dereekb.gae.utilities.task.IterableTask;

/**
 * {@link CreateTaskImpl} extension that adds an additional validation
 * {@link IterableTask} before attempting to save.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ValidatedCreateTaskImpl<T extends UniqueModel> extends CreateTaskImpl<T> {

	private CreateTaskValidator<T> validateTask;

	public ValidatedCreateTaskImpl(CreateTaskDelegate<T> delegate,
	        IterableTask<T> saveTask,
	        CreateTaskValidator<T> validateTask) {
		super(delegate, saveTask);
		this.setValidateTask(validateTask);
	}

	public ValidatedCreateTaskImpl(CreateTaskDelegate<T> delegate,
	        IterableTask<T> saveTask,
	        TaskRequestSender<T> sender,
	        CreateTaskValidator<T> validateTask) {
		super(delegate, saveTask, sender);
		this.setValidateTask(validateTask);
	}

	public CreateTaskValidator<T> getValidateTask() {
		return this.validateTask;
	}

	public void setValidateTask(CreateTaskValidator<T> validateTask) {
		if (validateTask == null) {
			throw new IllegalArgumentException("ValidateTask cannot be null.");
		}

		this.validateTask = validateTask;
	}

	// MARK: Override
	@Override
	public void usePairs(Iterable<CreatePair<T>> input,
	                     CreateTaskConfig configuration)
	        throws AtomicOperationException {

		// Do all updating.
		super.usePairs(input, configuration);

		try {
			this.validatePairs(input, configuration);
		} catch (AtomicOperationException e) {
			if (configuration.isAtomic()) {
				throw e;
			}
		}
	}

	protected void validatePairs(Iterable<CreatePair<T>> input,
	                             CreateTaskConfig configuration) {
		this.validateTask.doTask(input);
	}

}
