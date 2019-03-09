package com.dereekb.gae.web.taskqueue.model.extension.iterate;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.accessor.ModelKeyListAccessor;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;
import com.dereekb.gae.utilities.task.Task;

/**
 * Factory that generates a {@link Task} with {@link ModelKeyListAccessor} based
 * on the input.
 * 
 * @author dereekb
 *
 */
public interface TaskQueueIterateTaskFactory<T extends UniqueModel> {

	/**
	 * Creates a new task using the input.
	 * 
	 * @param input
	 *            {@link IterateTaskInput}. Never {@code null}.
	 * @return {@link Task}. Never {@code null}.
	 * 
	 * @throws FactoryMakeFailureException
	 *             thrown if the task cannot be generated.
	 */
	public Task<ModelKeyListAccessor<T>> makeTask(IterateTaskInput input) throws FactoryMakeFailureException;

}
