package com.dereekb.gae.web.taskqueue.controller.extension.iterate.request;

import com.dereekb.gae.web.taskqueue.controller.extension.iterate.IterateTask;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.IterateTaskInput;
import com.google.appengine.api.datastore.Cursor;

/**
 * Delegate for {@link IterateTask} for scheduling continuation of an iterate
 * task.
 *
 * @author dereekb
 *
 */
public interface IterateTaskContinuation {

	public void continueTask(IterateTaskInput input,
	                         Cursor cursor);

}
