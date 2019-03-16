package com.dereekb.gae.model.extension.taskqueue.task.deprecated.iterate;

import java.util.List;

import com.dereekb.gae.model.extension.taskqueue.deprecated.api.CustomTaskInfo;

/**
 * Single-use subtask run by an {@link IterateModelsTask} instance.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public interface IterateModelsSubtask<T> {

	/**
	 * Called before the task begins. Allows for any initialization, etc.
	 *
	 * @param request
	 */
	public void initTask(CustomTaskInfo request);

	/**
	 * Called to use the batch of iterated models.
	 *
	 * @param batch
	 */
	public void useModelBatch(List<T> batch);

	/**
	 * Called after the task has finished iterating available models.
	 */
	public void endTask();

}
