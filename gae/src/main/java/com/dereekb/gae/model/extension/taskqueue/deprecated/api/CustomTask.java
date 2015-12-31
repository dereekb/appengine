package com.dereekb.gae.model.extension.taskqueue.deprecated.api;

/**
 * A custom task for the internal Task Queue Controllers.
 *
 * @author dereekb
 * @deprecated Replaced by {@link Task}
 */
@Deprecated
public interface CustomTask {

	/**
	 * Processes the custom task.
	 *
	 * @param request
	 */
	public void doTask(CustomTaskInfo request);

}
