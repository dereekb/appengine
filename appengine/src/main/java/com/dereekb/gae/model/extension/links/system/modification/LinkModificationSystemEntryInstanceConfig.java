package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

import com.dereekb.gae.model.crud.task.config.AtomicTaskConfig;

/**
 * {@link LinkModificationSystemEntryInstance} configuration.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemEntryInstanceConfig extends AtomicTaskConfig {

	/**
	 * Returns the list of modification pairs.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LinkModificationPair> getModificationPairs();
	
}
