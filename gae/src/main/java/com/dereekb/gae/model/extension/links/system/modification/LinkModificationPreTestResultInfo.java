package com.dereekb.gae.model.extension.links.system.modification;

import java.util.Set;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;

/**
 * Pre-test result information.
 * 
 * @author dereekb
 * 
 * @see LinkModificationPreTestResult
 */
public interface LinkModificationPreTestResultInfo {

	/**
	 * Whether or not the pre-check passed or not.
	 * 
	 * @return {@code true} if passed.
	 */
	public boolean isPassed();

	/**
	 * Whether or not the primary key is missing.
	 * 
	 * @return {@code true} if the primary key is missing.
	 */
	public boolean isMissingPrimaryKey();
	
	/**
	 * Returns the keys set of all missing/unavailable models.
	 * 
	 * @return {@link Set}. Never {@code null}.
	 */
	public Set<ModelKey> getMissingTargetKeys();

}
