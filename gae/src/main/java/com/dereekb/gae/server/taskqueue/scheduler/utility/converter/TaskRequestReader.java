package com.dereekb.gae.server.taskqueue.scheduler.utility.converter;

import java.util.Collection;

import com.dereekb.gae.server.taskqueue.scheduler.TaskParameter;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;
import com.dereekb.gae.server.taskqueue.scheduler.TaskRequestTiming;
import com.google.appengine.api.taskqueue.TaskOptions.Method;

/**
 * Used for reading a {@link TaskRequest} from the context of the taskqueue.
 * 
 * @author dereekb
 *
 */
public interface TaskRequestReader {

	public TaskRequest getTaskRequest();

	public String getName();

	/**
	 * Returns the full request URI to the taskqueue.
	 * 
	 * @return {@link String}. Never {@code null}.
	 */
	public String getFullRequestUri();

	public Method getMethod();

	public TaskRequestTiming getTiming();

	public Collection<TaskParameter> getHeaders();

	public Collection<TaskParameter> getParameters();

}
