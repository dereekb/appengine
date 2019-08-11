package com.dereekb.gae.server.search.request;

/**
 * Used for indexing items new items.
 *
 * @author dereekb
 *
 */
public interface SearchServiceIndexRequest
        extends SearchServiceRequest {

	/**
	 * Returns the iterable set of document pairs to update.
	 *
	 * @return {@link Iterable}. Never {@code null}.
	 */
	public Iterable<SearchServiceIndexRequestPair> getRequestPairs();

}
