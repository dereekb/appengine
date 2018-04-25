package com.dereekb.gae.web.taskqueue.model.crud;

import java.util.List;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;
import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateControllerEntry;

/**
 * Delegate entry for {@link TaskQueueEditController}.
 *
 * @author dereekb
 *
 * @deprecated Use {@link TaskQueueIterateControllerEntry} with the
 *             {@link TaskQueueIterateController} instead.
 */
@Deprecated
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
	public void processDelete(List<ModelKey> keys);

}
