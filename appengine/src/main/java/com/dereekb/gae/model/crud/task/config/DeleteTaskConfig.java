package com.dereekb.gae.model.crud.task.config;

/**
 * {@link AtomicTaskConfig} extension for a delete task.
 *
 * @author dereekb
 *
 */
public interface DeleteTaskConfig
        extends AtomicTaskConfig {

	/**
	 * Whether or not to force delete by skipping the delete filter.
	 *
	 * @return {@code true} if delete should be forced.
	 */
	public boolean isForceDelete();

}
