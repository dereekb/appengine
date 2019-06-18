package com.dereekb.gae.server.taskqueue.scheduler;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.dereekb.gae.server.taskqueue.scheduler.exception.TaskAlreadyExistsException;

/**
 * Enqueues the input tasks into the taskqueue system.
 * <p>
 * Generally used directly by {@link TaskScheduler} implementations as the last step of enqueuing tasks.
 *
 * @author dereekb
 *
 */
public interface TaskSchedulerEnqueuer {

	/**
	 * Schedules a new task at the specified request.
	 *
	 * @param request
	 *            {@link SecuredTaskRequest}. Never {@code null}.
	 * @throws SubmitTaskException
	 * @throws TaskAlreadyExistsException
	 */
	public void enqueue(SecuredTaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException;

	/**
	 * Enqueues multiple tasks.
	 *
	 * @param requests
	 *            Collection of {@link SecuredTaskRequest} instances. Never
	 *            {@code null}.
	 * @throws SubmitTaskException
	 * @throws TaskAlreadyExistsException
	 */
	public void enqueue(Collection<? extends SecuredTaskRequest> requests)
	        throws SubmitTaskException,
	            TaskAlreadyExistsException;

}
