package com.dereekb.gae.web.taskqueue.model.extension.iterate;

import java.util.List;

import com.dereekb.gae.model.extension.iterate.IterateTaskInput;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Used by {@link TaskQueueIterateController} to perform sequence actions on
 * specific model keys.
 *
 * @author dereekb
 */
public interface SequenceTaskRequest {

	/**
	 * Returns the task input.
	 *
	 * @return {@link IterateTaskInput}. Never {@code null}.
	 */
	public IterateTaskInput getTaskInput();

	/**
	 * Returns the sequence of model keys.
	 *
	 * @return {@link List}. Never {@code null}.
	 */
	public List<ModelKey> getSequence();

}
