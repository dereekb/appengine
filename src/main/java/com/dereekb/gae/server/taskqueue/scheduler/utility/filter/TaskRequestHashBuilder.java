package com.dereekb.gae.server.taskqueue.scheduler.utility.filter;

import com.dereekb.gae.server.taskqueue.scheduler.TaskRequest;

/**
 * Used for generating hash codes for {@link TaskRequest} instances based on
 * their content.
 *
 * @author dereekb
 *
 */
public interface TaskRequestHashBuilder {

	public Integer readHashCode(TaskRequest request);

}
