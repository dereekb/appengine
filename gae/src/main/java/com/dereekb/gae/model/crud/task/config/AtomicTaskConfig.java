package com.dereekb.gae.model.crud.task.config;

/**
 * Configuration interface used by atomic tasks.
 *
 * @author dereekb
 *
 */
public interface AtomicTaskConfig {

	/**
	 * Whether or not to execute atomically.
	 * <p>
	 * If true, all changes must be successful, or nothing happens.
	 * 
	 * @return {@code true} if atomic.
	 */
	public boolean isAtomic();

}
