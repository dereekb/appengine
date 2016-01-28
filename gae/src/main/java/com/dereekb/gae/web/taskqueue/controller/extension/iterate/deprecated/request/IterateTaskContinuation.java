package com.dereekb.gae.web.taskqueue.controller.extension.iterate.old.request;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.web.taskqueue.controller.extension.iterate.deprecated.impl.IterateTask;
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
