package com.dereekb.gae.model.extension.links.system.modification.components;

import java.util.List;

/**
 * Used as a container for {@link LinkModificationResult} instances.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationResultSet {

	/**
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LinkModificationResult> getResults();

}
