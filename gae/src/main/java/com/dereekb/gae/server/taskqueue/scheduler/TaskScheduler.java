package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;

/**
 * Used for scheduling tasks.
 *
 * @author dereekb
 *
 */
public interface TaskScheduler {

	/**
	 * Schedules a new task at the specified request.
	 *
	 * @param request
	 *            {@link TaskRequest}. Never {@code null}.
	 * @throws SubmitTaskException
	 * @throws TaskAlreadyExistsException
	 */
	public void schedule(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException;

	/**
	 * Schedules multiple tasks.
	 *
	 * @param requests
	 *            Collection of {@link TaskRequest} instances. Never
	 *            {@code null}.
	 * @throws SubmitTaskException
	 * @throws TaskAlreadyExistsException
	 */
	public void schedule(Collection<TaskRequest> requests) throws SubmitTaskException, TaskAlreadyExistsException;

}
