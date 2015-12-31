package com.dereekb.gae.model.extension.taskqueue.task.iterate;

import com.dereekb.gae.model.extension.taskqueue.deprecated.api.CustomTaskInfo;

/**
 * Delegate responsible for scheduling the continuation of an iteration.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface IterateModelsTaskContinueDelegate {

	/**
	 * Schedules the continuation of the current model iteration.
	 *
	 * @param request
	 *            Request
	 * @param cursorString
	 *            Cursor the models ended off at
	 */
	public void continueIteration(CustomTaskInfo request,
	                              String cursorString);

}
