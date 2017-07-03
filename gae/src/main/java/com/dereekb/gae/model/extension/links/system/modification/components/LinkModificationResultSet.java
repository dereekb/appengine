package com.dereekb.gae.model.extension.links.system.modification.components;

import java.util.Set;

/**
 * Used as a container for {@link LinkModificationResult} instances.
 * 
 * @author dereekb
 *
 */
public interface LinkModificationResultSet {

	/**
	 * @return {@code true} if the model was modified.
	 */
	public boolean isModelModified();

	/**
	 * Set of results.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<LinkModificationResult> getResults();

}
