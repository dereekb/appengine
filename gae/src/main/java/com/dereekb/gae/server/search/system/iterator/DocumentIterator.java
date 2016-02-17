package com.dereekb.gae.server.search.system.iterator;

import com.dereekb.gae.server.search.system.exception.DocumentIterationBoundsException;

public interface DocumentIterator {

	public String getIndexName();

	public DocumentIteratorBatch getNextBatch() throws DocumentIterationBoundsException;

}
