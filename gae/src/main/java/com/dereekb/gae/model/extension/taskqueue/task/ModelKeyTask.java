package com.dereekb.gae.model.extension.taskqueue.task;

import java.util.Collection;

import com.dereekb.gae.model.crud.services.components.ReadService;
import com.dereekb.gae.model.crud.services.exception.AtomicOperationException;
import com.dereekb.gae.model.crud.services.request.ReadRequest;
import com.dereekb.gae.model.crud.services.request.impl.KeyReadRequest;
import com.dereekb.gae.model.crud.services.request.options.ReadRequestOptions;
import com.dereekb.gae.model.crud.services.response.ReadResponse;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.task.IterableTask;
import com.dereekb.gae.utilities.task.Task;
import com.dereekb.gae.utilities.task.exception.FailedTaskException;

/**
 * {@link Task} implementation that loads models specified by the
 * {@link ReadService} before running another {@link Task} that uses those
 * models.
 *
 * @author dereekb
 *
 * @param <T>
 *            input model
 */
public class ModelKeyTask<T extends UniqueModel>
        implements IterableTask<ModelKey> {

	private ReadService<T> readService;
	private ReadRequestOptions readOptions;
	private IterableTask<T> task;

	public ModelKeyTask() {}

	public ModelKeyTask(ReadService<T> readService, ReadRequestOptions readOptions, IterableTask<T> task) {
		this.readService = readService;
		this.readOptions = readOptions;
		this.task = task;
	}

	public ReadService<T> getReadService() {
		return this.readService;
	}

	public void setReadService(ReadService<T> readService) {
		this.readService = readService;
	}

	public ReadRequestOptions getReadOptions() {
		return this.readOptions;
	}

	public void setReadOptions(ReadRequestOptions readOptions) {
		this.readOptions = readOptions;
	}

	public IterableTask<T> getTask() {
		return this.task;
	}

	public void setTask(IterableTask<T> task) {
		this.task = task;
	}

	// MARK: Task
	@Override
	public void doTask(Iterable<ModelKey> input) throws FailedTaskException {
		Collection<T> models = null;

		try {
			ReadRequest request = new KeyReadRequest(input, this.readOptions);
			ReadResponse<T> response = this.readService.read(request);
			models = response.getModels();
		} catch (AtomicOperationException e) {
			throw new FailedTaskException("All specified models for the task.", e);
		}

		this.task.doTask(models);
	}

	@Override
	public String toString() {
		return "ModelKeyTask [readService=" + this.readService + ", readOptions=" + this.readOptions + ", task="
		        + this.task + "]";
	}

}
