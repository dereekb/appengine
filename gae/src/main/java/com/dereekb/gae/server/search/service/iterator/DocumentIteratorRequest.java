package com.dereekb.gae.server.search.service.iterator;

import com.dereekb.gae.server.search.service.request.DocumentRangeReadRequest;


public interface DocumentIteratorRequest
        extends DocumentRangeReadRequest {

	/**
	 * Size that each retrieved batch of elements should be.
	 *
	 * @return {@link Integer} for size, or {@code null} if not specified.
	 */
	public Integer getBatchSize();

}
