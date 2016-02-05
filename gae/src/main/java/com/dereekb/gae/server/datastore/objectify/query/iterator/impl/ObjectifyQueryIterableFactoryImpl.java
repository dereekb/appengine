package com.dereekb.gae.server.datastore.objectify.query.iterator.impl;

import java.util.Map;
import java.util.NoSuchElementException;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilderFactory;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryRequestOptionsImpl;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterable;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterator;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterable;
import com.dereekb.gae.utilities.collections.iterator.index.exception.InvalidIteratorIndexException;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;

/**
 * {@link IndexedIterable} implementation using a {@link ObjectifyQueryService},
 * which is used to iterate over models in the database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryIterableFactoryImpl<T extends ObjectifyModel<T>>
        implements ObjectifyQueryIterableFactory<T> {

	private int iterateLimit = MAX_ITERATION_LIMIT;

	private ObjectifyQueryRequestBuilderFactory<T> queryBuilderFactory;

	public ObjectifyQueryIterableFactoryImpl(ObjectifyQueryRequestBuilderFactory<T> queryBuilderFactory) {
	    this.queryBuilderFactory = queryBuilderFactory;
	}

	public ObjectifyQueryRequestBuilderFactory<T> getQueryBuilderFactory() {
		return this.queryBuilderFactory;
	}

	public void setQueryBuilderFactory(ObjectifyQueryRequestBuilderFactory<T> queryBuilderFactory) {
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
			throw new IllegalArgumentException("Iterate limit restricted to between 1 and " + MAX_ITERATION_LIMIT + ".");
		}

		this.iterateLimit = iterateLimit;
	}

	@Override
    public IterableInstance makeIterable() {
		return new IterableInstance();
	}

	@Override
    public IterableInstance makeIterable(Cursor cursor) {
		return new IterableInstance(cursor);
	}

	@Override
    public IterableInstance makeIterable(Cursor startCursor,
	                                     Map<String, String> parameters) {
		return new IterableInstance(startCursor, parameters);
	}

	// MARK: Internal Classes
	public class IterableInstance
	        implements ObjectifyQueryIterable<T> {

		private Cursor startCursor;
		private Map<String, String> parameters;

		public IterableInstance() {
			this(null, null);
		}

		public IterableInstance(Cursor startCursor) {
			this(startCursor, null);
		}

		public IterableInstance(Cursor startCursor, Map<String, String> parameters) {
			this.setStartCursor(startCursor);
			this.setParameters(parameters);
		}

		public Cursor getStartCursor() {
			return this.startCursor;
		}

        public void setStartCursor(Cursor startCursor) {
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
			this.startCursor = IndexUtility.convertIndex(index);
		}

		@Override
		public IteratorInstance iterator() {
			return new IteratorInstance(this.startCursor, this.parameters);
		}

	}

	// MARK: Instance
	/**
	 * Single-use query iteration.
	 *
	 * @author dereekb
	 */
	public class IteratorInstance
	        implements ObjectifyQueryIterator<T> {

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
		 * Custom, optional parameters to feed to {@link #initializer}.
		 */
		private Map<String, String> parameters;

		/**
		 * Whether or not the iterator has finished.
		 */
		private boolean finished = false;

		private int iteratorBatchLimit;

		private IteratorInstance(Cursor startCursor, Map<String, String> parameters) {
			this.iteratorBatchLimit = ObjectifyQueryIterableFactoryImpl.this.iterateLimit;
			this.startCursor = startCursor;
			this.parameters = parameters;
		}

		// MARK: LimitedIterator
		public int getIteratorBatchLimit() {
			return this.iteratorBatchLimit;
		}

		public void setIteratorBatchLimit(int iteratorLimit) throws IllegalArgumentException {
			if (iteratorLimit < 1 || iteratorLimit > MAX_ITERATION_LIMIT) {
				throw new IllegalArgumentException("Iterate limit restricted to between 1 and " + MAX_ITERATION_LIMIT + ".");
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

		protected ObjectifyQueryRequestBuilder<T> newQuery() {
			return ObjectifyQueryIterableFactoryImpl.this.queryBuilderFactory.makeQuery(this.parameters);
		}

		private QueryResultIterator<T> continueQuery() {
			ObjectifyQueryRequestBuilder<T> query = this.newQuery();
			ObjectifyQueryRequestOptionsImpl options = new ObjectifyQueryRequestOptionsImpl();
			options.setLimit(this.iteratorBatchLimit);

			if (this.iteratorCursor != null) {
				options.setCursor(this.iteratorCursor);
			} else if (this.startCursor != null) {
				options.setCursor(this.startCursor);
			}

			query.setOptions(options);
			ExecutableObjectifyQuery<T> executable = query.buildExecutableQuery();
			QueryResultIterable<T> iterable = executable.queryModelsIterable();
			return iterable.iterator();
		}

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
        public Cursor getStartCursor() {
			return this.startCursor;
		}


		@Override
        public Cursor getCurrentCursor() {
			Cursor cursor = null;

			if (this.iterator != null) {
				cursor = this.iterator.getCursor();
			}

			return cursor;
		}

		@Override
        public Cursor getEndCursor() {
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
		public ModelKey getCurrentIndex() throws UnavailableIteratorIndexException {
			return IndexUtility.safeConvertCursor(this.getCurrentCursor());
		}

		@Override
		public ModelKey getEndIndex() throws UnavailableIteratorIndexException {
			return IndexUtility.safeConvertCursor(this.getEndCursor());
		}

	}

	/**
	 * Used for conversions.
	 *
	 * @author dereekb
	 */
	private static class IndexUtility {

		private static ModelKey safeConvertCursor(Cursor cursor) {
			if (cursor == null) {
				throw new UnavailableIteratorIndexException("Index was unavailable.");
			} else {
				return convertCursor(cursor);
			}
		}

		private static ModelKey convertCursor(Cursor cursor) {
			ModelKey index = null;

			if (cursor != null) {
				index = ModelKey.safe(cursor.toWebSafeString());
			}

			return index;
		}

		private static Cursor convertIndex(ModelKey index) throws InvalidIteratorIndexException {
			Cursor cursor = null;

			if (index != null) {
				String cursorString = index.getName();

				if (cursorString == null) {
					throw new InvalidIteratorIndexException("Expected web-safe cursor string value.");
				} else {
					cursor = Cursor.fromWebSafeString(index.getName());
				}
			}

			return cursor;
		}

	}

	@Override
	public String toString() {
		return "IterableObjectifyQueryImpl [iterateLimit=" + this.iterateLimit + ", queryBuilderFactory="
		        + this.queryBuilderFactory + "]";
	}

}
