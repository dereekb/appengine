package com.dereekb.gae.server.datastore.models.query.iterator.impl;

import java.util.Map;
import java.util.NoSuchElementException;

import com.dereekb.gae.server.datastore.models.UniqueModel;
import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestBuilder;
import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryRequestBuilderFactory;
import com.dereekb.gae.server.datastore.models.query.iterator.ExecutableIndexedModelQuery;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterable;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterableFactory;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterator;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.dereekb.gae.utilities.collections.iterator.cursor.impl.ResultsCursorImpl;
import com.dereekb.gae.utilities.collections.iterator.index.exception.InvalidIteratorIndexException;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * {@link IndexedModelQueryIterableFactory} implementation using a
 * {@link IndexedModelQueryService},
 * which is used to iterate over models in the database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class IndexedModelQueryIterableFactoryImpl<T extends UniqueModel>
        implements IndexedModelQueryIterableFactory<T> {

	public static final Integer MIN_ITERATION_LIMIT = 1;
	public static final Integer MAX_ITERATION_LIMIT = 1000;

	/**
	 * The maximum amount of models to iterate over in a single instance.
	 */
	private int iterateLimit = MAX_ITERATION_LIMIT;

	private IndexedModelQueryRequestBuilderFactory<T> queryBuilderFactory;

	public IndexedModelQueryIterableFactoryImpl(IndexedModelQueryRequestBuilderFactory<T> queryBuilderFactory) {
		this.queryBuilderFactory = queryBuilderFactory;
	}

	public IndexedModelQueryRequestBuilderFactory<T> getQueryBuilderFactory() {
		return this.queryBuilderFactory;
	}

	public void setQueryBuilderFactory(IndexedModelQueryRequestBuilderFactory<T> queryBuilderFactory) {
		if (queryBuilderFactory == null) {
			throw new IllegalArgumentException("Query Builder cannot be null.");
		}

		this.queryBuilderFactory = queryBuilderFactory;
	}

	public int getIterateLimit() {
		return this.iterateLimit;
	}

	public void setIterateLimit(int iterateLimit) throws IllegalArgumentException {
		if (iterateLimit < 1 || iterateLimit > MAX_ITERATION_LIMIT) {
			throw new IllegalArgumentException(
			        "Iterate limit restricted to between 1 and " + MAX_ITERATION_LIMIT + ".");
		}

		this.iterateLimit = iterateLimit;
	}

	// MARK: IndexedModelQueryIterableFactory
	@Override
	public IterableInstance makeIterable() {
		return new IterableInstance();
	}

	@Override
	public IndexedModelQueryIterable<T> makeIterable(ResultsCursor cursor) {
		return new IterableInstance(cursor);
	}

	@Override
	public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters) {
		return this.makeIterable(parameters, null);
	}

	@Override
	public IndexedModelQueryIterable<T> makeIterable(Map<String, String> parameters,
	                                                 ResultsCursor cursor) {
		return new IterableInstance(parameters, cursor);
	}

	// MARK: Internal Classes
	public class IterableInstance
	        implements IndexedModelQueryIterable<T> {

		private ResultsCursor startCursor;
		private Map<String, String> parameters;

		public IterableInstance() {}

		public IterableInstance(ResultsCursor startCursor) {
			this.setStartCursor(startCursor);
		}

		public IterableInstance(Map<String, String> parameters, ResultsCursor startCursor) {
			this.setStartCursor(startCursor);
			this.setParameters(parameters);
		}

		@Override
		public ResultsCursor getStartCursor() {
			return this.startCursor;
		}

		@Override
		public void setStartCursor(ResultsCursor startCursor) {
			this.startCursor = startCursor;
		}

		public Map<String, String> getParameters() {
			return this.parameters;
		}

		public void setParameters(Map<String, String> parameters) {
			this.parameters = parameters;
		}

		// MARK: IndexedIterable
		@Override
		public ModelKey getStartIndex() {
			return IndexUtility.convertCursor(this.startCursor);
		}

		@Override
		public void setStartIndex(ModelKey index) throws InvalidIteratorIndexException {
			this.startCursor = IndexUtility.convertCursor(index);
		}

		@Override
		public IteratorInstance iterator() {
			return new ConfiguredIteratorInstance(this.startCursor, this.parameters);
		}

	}

	// MARK: Instance
	private class ConfiguredIteratorInstance extends IteratorInstance {

		private SimpleQuery<T> query;

		protected ConfiguredIteratorInstance(Cursor startCursor) {
			super(startCursor);
		}

		private ConfiguredIteratorInstance(Cursor startCursor, SimpleQuery<T> query) {
			super(startCursor);
			this.setQuery(query);
		}

		private ConfiguredIteratorInstance(Cursor startCursor, Map<String, String> parameters) {
			super(startCursor);
			this.setQuery(parameters);
		}

		public void setQuery(Map<String, String> parameters) {
			this.setQuery(this.makeQuery(parameters));
		}

		public void setQuery(SimpleQuery<T> query) {
			if (query == null) {
				query = this.makeQuery(null);
			}

			// Set Chunk size override.
			if (IndexedModelQueryIterableFactoryImpl.this.chunkSize != null) {
				query = query.chunk(IndexedModelQueryIterableFactoryImpl.this.chunkSize);
			}

			this.query = query;
		}

		protected SimpleQuery<T> makeQuery(Map<String, String> parameters) {
			IndexedModelQueryRequestBuilder<T> builder = IndexedModelQueryIterableFactoryImpl.this.queryBuilderFactory
			        .makeQuery(parameters);
			ExecutableIndexedModelQuery<T> query = builder.buildExecutableQuery();
			return query.getQuery();
		}

		@Override
		protected QueryResultIterator<T> continueQuery() {
			SimpleQuery<T> query = this.query;
			Cursor iteratorCursor = this.getIteratorCursor();

			query = query.startAt(iteratorCursor);
			query = query.limit(this.getIteratorBatchLimit());

			return query.iterator();
		}

	}

	/**
	 * Single-use query iteration.
	 *
	 * @author dereekb
	 */
	public abstract class IteratorInstance
	        implements IndexedModelQueryIterator<T> {

		/**
		 * The current index.
		 */
		private int iteratorIndex = 0;

		/**
		 * Cursor this iterator starts on.
		 */
		private Cursor startCursor;

		/**
		 * Cursor used by the iterator.
		 */
		private Cursor iteratorCursor;

		/**
		 * Cursor this iterator ended on.
		 */
		private Cursor endCursor;

		/**
		 * Current iterator.
		 */
		private QueryResultIterator<T> iterator;

		/**
		 * Whether or not the iterator has finished.
		 */
		private boolean finished = false;

		private int iteratorBatchLimit;

		private IteratorInstance(Cursor startCursor) {
			this.iteratorBatchLimit = IndexedModelQueryIterableFactoryImpl.this.iterateLimit;
			this.startCursor = startCursor;
			this.iteratorCursor = this.startCursor;
		}

		// MARK: LimitedIterator
		public int getIteratorBatchLimit() {
			return this.iteratorBatchLimit;
		}

		public void setIteratorBatchLimit(int iteratorLimit) throws IllegalArgumentException {
			if (iteratorLimit < 1 || iteratorLimit > MAX_ITERATION_LIMIT) {
				throw new IllegalArgumentException(
				        "Iterate limit restricted to between 1 and " + MAX_ITERATION_LIMIT + ".");
			}

			this.iteratorBatchLimit = iteratorLimit;
		}

		// MARK: IndexedIterator
		@Override
		public boolean hasNext() {
			if (this.finished) {
				return false;
			}

			QueryResultIterator<T> iterator = this.getIterator();
			boolean hasNext = iterator.hasNext();

			if (hasNext == false) {

				// Retrieve the next iterator/batch
				if (this.iteratorIndex >= this.iteratorBatchLimit) {
					iterator = this.getNextIterator();
					hasNext = iterator.hasNext();
					this.iteratorIndex = 0;
				}

				// If still false, then we're finished.
				if (hasNext == false) {
					this.finished();
				}
			}

			return hasNext;
		}

		/**
		 * Called when the iterator has finished iterating.
		 *
		 * Resets the iterator for reuse if reusable.
		 */
		private void finished() {
			this.endCursor = this.iterator.getCursor();
			this.finished = true;
		}

		@Override
		public void remove() {
			// Does nothing. Can choose to extend this class to allow removal
			// through a delegate, however.
		}

		@Override
		public T next() throws NoSuchElementException {
			T next = null;

			if (this.hasNext()) {
				next = this.iterator.next();
				this.iteratorIndex += 1;
			} else {
				throw new NoSuchElementException();
			}

			return next;
		}

		protected abstract QueryResultIterator<T> continueQuery();

		private QueryResultIterator<T> getNextIterator() {
			this.iteratorCursor = this.iterator.getCursor();
			this.iterator = null;
			return this.getIterator();
		}

		private QueryResultIterator<T> getIterator() {
			QueryResultIterator<T> iterator = this.iterator;

			if (iterator == null) {
				iterator = this.continueQuery();
				this.iterator = iterator;
			}

			return iterator;
		}

		@Override
		public ResultsCursor getStartCursor() {
			return this.startCursor;
		}

		public Cursor getIteratorCursor() {
			return this.iteratorCursor;
		}

		@Override
		public ResultsCursor getEndCursor() {
			Cursor cursor = null;

			if (this.iterator != null) {
				cursor = this.iterator.getCursor();
			} else {
				cursor = this.endCursor;
			}

			return cursor;
		}

		@Override
		public ModelKey getStartIndex() throws UnavailableIteratorIndexException {
			return IndexUtility.safeConvertCursor(this.getStartCursor());
		}

		@Override
		public ModelKey getEndIndex() throws UnavailableIteratorIndexException {
			return IndexUtility.safeConvertCursor(this.getEndCursor());
		}

	}

	/**
	 * Used for conversions and throwing the necessary exceptions.
	 *
	 * @author dereekb
	 */
	protected static class IndexUtility {

		protected static ModelKey safeConvertCursor(ResultsCursor cursor) throws UnavailableIteratorIndexException {
			if (cursor == null) {
				throw new UnavailableIteratorIndexException("Index was unavailable.");
			} else {
				return convertCursor(cursor.getCursorString());
			}
		}

		protected static ModelKey convertCursor(String cursorString) {
			ModelKey index = null;

			if (cursorString != null) {
				index = ModelKey.safe(cursorString);
			}

			return index;
		}

		protected static ResultsCursor convertIndex(ModelKey index) throws InvalidIteratorIndexException {
			ResultsCursor cursor = null;

			if (index != null) {
				String cursorString = index.getName();

				if (cursorString == null) {
					throw new InvalidIteratorIndexException("Expected a cursor string value.");
				} else {
					cursor = ResultsCursorImpl.make(cursorString);
				}
			}

			return cursor;
		}

	}

	@Override
	public String toString() {
		return "IterableIndexedModelQueryImpl [iterateLimit=" + this.iterateLimit + ", queryBuilderFactory="
		        + this.queryBuilderFactory + "]";
	}

}
