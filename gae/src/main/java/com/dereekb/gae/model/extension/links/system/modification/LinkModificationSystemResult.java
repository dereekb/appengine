package com.dereekb.gae.model.extension.links.system.modification;

import java.util.List;

import com.dereekb.gae.model.extension.links.system.modification.components.LinkModificationResult;
import com.dereekb.gae.model.extension.links.system.modification.exception.failure.LinkModificationSystemRequestFailure;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.utilities.misc.keyed.Keyed;
import com.dereekb.gae.utilities.misc.success.SuccessModel;

/**
 * The result after processing a {@link LinkModificationSystemRequest}. 
 *
 * @author dereekb
 * @see LinkModificationSystemChangesResult
 */
public interface LinkModificationSystemResult extends LinkModificationSystemRequestReference, LinkModificationSystemRequestFailure, SuccessModel, Keyed<ModelKey> {

	/**
	 * Returns the key value of the request.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ModelKey keyValue();
	
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
