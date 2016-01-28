package com.dereekb.gae.web.taskqueue.controller.extension.iterate;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.google.appengine.api.datastore.Cursor;

/**
 * Request that contains {@link IterateTaskInput} and allows scheduling a
 * continuation.
 *
 * @author dereekb
 *
 */
public interface IterateTaskRequest {

	/**
	 *
	 * @return {@link IterateTaskInput}. Never {@code null}.
	 */
	public IterateTaskInput getTaskInput();

	/**
	 * Schedules a continuation using the specified {@link Cursor}.
	 *
	 * @param cursor
	 *            {@link Cursor}. Never {@code null}.
	 */
	public void scheduleContinuation(Cursor cursor);

}
