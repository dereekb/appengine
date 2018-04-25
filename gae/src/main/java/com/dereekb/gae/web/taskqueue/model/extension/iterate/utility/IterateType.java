package com.dereekb.gae.web.taskqueue.model.extension.iterate.utility;

import com.dereekb.gae.web.taskqueue.model.extension.iterate.TaskQueueIterateController;

/**
 * {@link TaskQueueIterateController} iteration type.
 *
 * @author dereekb
 *
 */
public enum IterateType {

	/**
	 * Iterates over a sequence of models specified model identifiers.
	 */
	SEQUENCE,

	/**
	 * Iterates over all models, configured by a query.
	 */
	ITERATE;

	public String pathForTask(String modelType,
	                          String taskName) {
		switch (this) {
			case ITERATE:
				return TaskQueueIterateController.pathForIterateTask(modelType, taskName);
			case SEQUENCE:
				return TaskQueueIterateController.pathForSequenceTask(modelType, taskName);
			default:
				throw new UnsupportedOperationException();
		}
	}

}
