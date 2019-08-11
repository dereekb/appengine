package com.dereekb.gae.server.search.iterator;

/**
 * Used for retriving a {@link DocumentIndexIterator}.
 *
 * @author dereekb
 *
 */
public interface SearchServiceIterator {

	/**
	 * Creates a new {@link DocumentIndexIterator} for the input request.
	 *
	 * @param request
	 *            {@link DocumentIteratorRequest}. Never {@code null}.
	 * @return {@link DocumentIndexIterator}. Never {@code null}.
	 */
	public SearchServiceBatchIterator makeIndexIterator(DocumentIteratorRequest request);

}
