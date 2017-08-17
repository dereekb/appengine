package com.dereekb.gae.model.extension.links.system.modification;

import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;

/**
 * {@link LinkModificationSystemInstance} options.
 * 
 */
public interface LinkModificationSystemInstanceOptions extends AtomicTaskConfig {

	/**
	 * Whether or not to auto-commit the results returned by the instance.
	 * 
	 * @return {@code true} if changes should be auto-commited.
	 */
	public boolean isAutoCommit();
	
}
