package com.dereekb.gae.server.datastore.objectify.query.impl;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryModelResultIterator;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;
import com.google.cloud.datastore.QueryResults;

/**
 * {@link IndexedModelQueryModelResultIterator} implementation that wraps a
 * {@link IndexedModelQueryModelResultIterator}.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryModelResultIteratorImpl<T extends ObjectifyModel<T>>
        implements IndexedModelQueryModelResultIterator<T> {

	private final QueryResults<T> resultIterator;
	private final transient ObjectifyCursor startCursor;

	public ObjectifyQueryModelResultIteratorImpl(QueryResults<T> resultIterator) {
		this.resultIterator = resultIterator;

		// Get the initial cursor before iterating.
		this.startCursor = ObjectifyCursor.make(this.resultIterator.getCursorAfter());
	}

	public QueryResults<T> getResultIterator() {
		return this.resultIterator;
	}

	// MARK: IndexedModelQueryModelResultIterator
	@Override
	public ResultsCursor getStartCursor() throws UnavailableIteratorIndexException {
		return this.startCursor;
	}

	@Override
	public ResultsCursor getEndCursor() throws UnavailableIteratorIndexException {
		return ObjectifyCursor.make(this.resultIterator.getCursorAfter());
	}

	@Override
	public boolean hasNext() {
		return this.resultIterator.hasNext();
	}

	@Override
	public T next() {
		return this.resultIterator.next();
	}

	@Override
	public String toString() {
		return "ObjectifyQueryModelResultIteratorImpl [resultIterator=" + this.resultIterator + "]";
	}

}
