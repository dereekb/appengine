package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;

/**
 * The result after processing a {@link LinkModificationSystemRequest}. 
 *
 * @author dereekb
 * @see LinkModificationSystemChangesResult
 */
public interface LinkModificationSystemResult extends LinkModificationSystemRequestReference {

	/**
	 * Returns the pre-test results for this change.
	 * 
	 * @return {@link LinkModificationPreTestResult}. Never {@code null}.
	 */
	public LinkModificationPreTestResult getPreTestResults();
	
	/**
	 * Returns the primary result.
	 * 
	 * @return {@link LinkModificationResult}. Never {@code null}.
	 */
	public LinkModificationResult getPrimaryResult();
	
	/**
	 * Returns the secondary results from the synchronization.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<LinkModificationResult> getSynchronizationResults();

}
