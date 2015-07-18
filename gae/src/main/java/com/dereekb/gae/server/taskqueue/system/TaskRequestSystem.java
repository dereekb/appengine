package com.dereekb.gae.server.taskqueue.system;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.exception.SubmitTaskException;
import com.google.appengine.api.taskqueue.TaskAlreadyExistsException;

/**
 * System for submitting task requests.
 *
 * @author dereekb
 *
 */
public interface TaskRequestSystem {

	public void submitRequest(TaskRequest request) throws SubmitTaskException, TaskAlreadyExistsException;

	public void submitRequests(Collection<TaskRequest> request)
	        throws SubmitTaskException,
	            TaskAlreadyExistsException;

	// Later: Remove requests, for instances that use a unique name.

}
