package com.dereekb.gae.server.search.service.iterator;

import com.dereekb.gae.server.search.service.exception.DocumentIterationBoundsException;

public interface DocumentIterator {

	public String getIndexName();

	public DocumentIteratorBatch getNextBatch() throws DocumentIterationBoundsException;

}
