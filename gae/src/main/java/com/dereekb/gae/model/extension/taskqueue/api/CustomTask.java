package com.dereekb.gae.model.extension.taskqueue.api;

/**
 * A custom task for the internal Task Queue Controllers.
 *
 * @author dereekb
 */
public interface CustomTask {

	/**
	 * Processes the custom task.
	 *
	 * @param request
	 */
	public void doTask(CustomTaskInfo request);

}
