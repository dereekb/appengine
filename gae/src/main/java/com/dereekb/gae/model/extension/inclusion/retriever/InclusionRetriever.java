package com.dereekb.gae.model.extension.inclusion.retriever;


/**
 * Used to retrieve related models.
 *
 * @author dereekb
 */
public interface InclusionRetriever {

	/**
	 * Builds a {@link InclusionRetrieverOutput} instance using the input.
	 *
	 * @param input
	 *            {@link InclusionRetrieverInput} instance.
	 * @return {@link InclusionRetrieverOutput} instance. Never null.
	 */
	public InclusionRetrieverOutput retrieveRelated(InclusionRetrieverInput input);

}
