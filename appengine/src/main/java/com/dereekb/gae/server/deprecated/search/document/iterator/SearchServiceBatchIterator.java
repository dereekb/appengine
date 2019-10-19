package com.dereekb.gae.server.search.iterator;

import com.dereekb.gae.server.search.components.SearchServiceIndex;
import com.dereekb.gae.server.search.exception.DocumentIterationBoundsException;

public interface SearchServiceBatchIterator extends SearchServiceIndex {

	public DocumentIteratorBatch getNextBatch() throws DocumentIterationBoundsException;

}
