package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

/**
 * {@link LinkModificationSystemInstance} result.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationSystemChangesResult extends LinkModificationSystemChangeInstance {
	
	/**
	 * Returns a list of results.
	 * 
	 * @return {@link LinkModificationSystemResult}.
	 */
	public List<LinkModificationSystemResult> getResults();
	
}
