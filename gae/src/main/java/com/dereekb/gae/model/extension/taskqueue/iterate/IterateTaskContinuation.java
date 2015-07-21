package com.dereekb.gae.model.extension.taskqueue.iterate;

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
