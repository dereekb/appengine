package com.dereekb.gae.model.extension.links.service;

import java.util.List;

import com.dereekb.gae.model.extension.links.service.exception.LinkServiceChangeSetException;
import com.dereekb.gae.model.extension.links.system.modification.LinkModificationSystemResult;
import com.dereekb.gae.utilities.collections.pairs.SuccessPair;

/**
 * Response returned from a {@link LinkService}.
 * 
 * @author dereekb
 *
 */
public interface LinkServiceResponse {
	
	/**
	 * Returns a set of {@link SuccessPair} values.
	 * 
	 * @return {@link List}. Never {@code null}.
	 */
	public List<SuccessPair<LinkModificationSystemResult>> getSuccessResults();

	/**
	 * Creates a new {@link LinkServiceChangeSetException} comprised of all
	 * errors.
	 * 
	 * @return {@link LinkServiceChangeSetException}. Never {@code null}.
	 */
	public LinkServiceChangeSetException getErrorsSet();

}
