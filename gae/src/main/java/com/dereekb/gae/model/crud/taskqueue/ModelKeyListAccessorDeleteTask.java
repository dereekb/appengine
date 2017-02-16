package com.dereekb.gae.model.crud.taskqueue;

import com.dereekb.gae.model.crud.services.components.DeleteService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.DeleteRequest;
import com.dereekb.gae.model.crud.services.request.impl.DeleteRequestImpl;
import com.dereekb.gae.model.crud.services.request.options.DeleteRequestOptions;
import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;
import com.dereekb.gae.web.taskqueue.model.crud.task.TaskQueueDeleteModelTask;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateTaskFactory;

/**
 * {@link Task} that uses a {@link DeleteService} to delete the input.
 * 
 * @author dereekb
 *
 * @param <T>
 *            model type
 * @see TaskQueueDeleteModelTask
 */
public class ModelKeyListAccessorDeleteTask<T extends UniqueModel>
        implements Task<ModelKeyListAccessor<T>>, TaskQueueIterateTaskFactory<T> {

	private DeleteService<T> deleteService;
	private DeleteRequestOptions options;

	public ModelKeyListAccessorDeleteTask(DeleteService<T> deleteService) throws IllegalArgumentException {
		this.setDeleteService(deleteService);
	}

	public ModelKeyListAccessorDeleteTask(DeleteService<T> deleteService, DeleteRequestOptions options)
	        throws IllegalArgumentException {
		this.setDeleteService(deleteService);
		this.setOptions(options);
	}

	public DeleteService<T> getDeleteService() {
		return this.deleteService;
	}

	public void setDeleteService(DeleteService<T> deleteService) {
		if (deleteService == null) {
			throw new IllegalArgumentException("DeleteService cannot be null.");
		}

		this.deleteService = deleteService;
	}

	public DeleteRequestOptions getOptions() {
		return this.options;
	}

	public void setOptions(DeleteRequestOptions options) {
		this.options = options;
	}

	// MARK: Task
	@Override
	public void doTask(ModelKeyListAccessor<T> input) throws FailedTaskException {
		DeleteRequest request = new DeleteRequestImpl(input.getModelKeys(), this.options);

		try {
			this.deleteService.delete(request);
		} catch (AtomicOperationException e) {
			throw new FailedTaskException(e);
		}
	}

	// MARK: Task Factory
	@Override
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException {
		return this;
	}

	@Override
	public String toString() {
		return "ModelKeyListAccessorDeleteTask [deleteService=" + this.deleteService + ", options=" + this.options
		        + "]";
	}

}
