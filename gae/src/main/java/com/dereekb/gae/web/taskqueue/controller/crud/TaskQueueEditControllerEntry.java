package com.dereekb.gae.web.taskqueue.controller.crud;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Delegate entry for {@link TaskQueueEditController}.
 *
 * @author dereekb
 *
 */
public interface TaskQueueEditControllerEntry {

	/**
	 * Performs a task for reviewing created models at the specified keys.
	 *
	 * @param keys
	 *            {@link ModelKey} list. Never {@code null}.
	 */
	public void reviewCreate(List<ModelKey> keys);

	/**
	 * Performs a task for reviewing updates made to the models for the
	 * specified keys.
	 *
	 * @param keys
	 *            {@link ModelKey} list. Never {@code null}.
	 */
	public void reviewUpdate(List<ModelKey> keys);

	/**
	 * Performs a task to review the delete made to the models for the specified
	 * keys.
	 *
	 * @param keys
	 *            {@link ModelKey} list. Never {@code null}.
	 */
	public void reviewDelete(List<ModelKey> keys);

}
