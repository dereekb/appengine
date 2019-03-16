package com.dereekb.gae.server.taskqueue.system;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.exception.SubmitTaskException;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;

/**
 * System for submitting task requests.
 *
 * @author dereekb
 *
 */
@Deprecated
public interface TaskRequestSystem {

	public void submitRequest(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException;

	public void submitRequests(Collection<TaskRequest> request)
	        throws SubmitTaskException,
	            TaskAlreadyExistsException;

	// TODO Later: Remove requests, for instances that use a unique name.

}
