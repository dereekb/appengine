package com.dereekb.gae.web.api.taskqueue.controller.crud;

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
	 * Performs a create task for the specified keys.
	 *
	 * @param keys
	 */
	public void create(List<ModelKey> keys);

	/**
	 * Performs an update task for the specified keys.
	 *
	 * @param keys
	 */
	public void update(List<ModelKey> keys);

	/**
	 * Performs a delete task for the specified keys.
	 * 
	 * @param keys
	 */
	public void delete(List<ModelKey> keys);

}
