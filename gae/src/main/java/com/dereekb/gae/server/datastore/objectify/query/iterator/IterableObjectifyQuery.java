package com.dereekb.gae.server.datastore.objectify.query.iterator;

import java.util.ArrayList;
import java.util.Map;

import com.dereekb.gae.server.datastore.models.keys.ModelKey;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.ObjectifyKeyedQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterable;
import com.dereekb.gae.utilities.collections.iterator.index.IndexedIterator;
import com.dereekb.gae.utilities.collections.iterator.index.exception.InvalidIteratorIndexException;
import com.dereekb.gae.utilities.collections.iterator.index.exception.UnavailableIteratorIndexException;
import com.dereekb.gae.utilities.collections.iterator.limit.LimitedIterator;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;

/**
 *
 *
 * @author dereekb
 *
 * @param <T>
 */
public class IterableObjectifyQuery<T extends ObjectifyModel<T>>
        implements IndexedIterable<T> {

	public static final Integer MAX_LIMIT = 1000;

	private IterableObjectifyQueryInitializer<T> initializer;
	private ObjectifyKeyedQuery<T> query;

	private int iterateLimit = MAX_LIMIT;
	private Cursor startCursor;

	public IterableObjectifyQuery(ObjectifyKeyedQuery<T> query) {
		this.setQuery(query);
	}

	public IterableObjectifyQuery(IterableObjectifyQueryInitializer<T> initializer, ObjectifyKeyedQuery<T> query) {
		this.initializer = initializer;
		this.setQuery(query);
	}

	public IterableObjectifyQueryInitializer<T> getInitializer() {
		return this.initializer;
	}

	public void setInitializer(IterableObjectifyQueryInitializer<T> initializer) {
		this.initializer = initializer;
	}

	public ObjectifyKeyedQuery<T> getQuery() {
		return this.query;
	}

	public void setQuery(ObjectifyKeyedQuery<T> query) throws IllegalArgumentException {
		if (query == null) {
			throw new IllegalArgumentException("Query cannot be null.");
		}

		this.query = query;
	}

	public int getIterateLimit() {
		return this.iterateLimit;
	}

	public void setIterateLimit(int iterateLimit) throws IllegalArgumentException {
		if (iterateLimit < 1 || iterateLimit > MAX_LIMIT) {
			throw new IllegalArgumentException("Iterate limit restricted to between 1 and " + MAX_LIMIT + ".");
		}

		this.iterateLimit = iterateLimit;
	}

	public Cursor getStartCursor() {
		return this.startCursor;
	}

	public void setStartCursor(Cursor startCursor) {
		this.startCursor = startCursor;
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
	public IteratorInstance indexedIterator() {
		return new IteratorInstance();
	}

	@Override
	public IteratorInstance iterator() {
		return this.indexedIterator();
	}

	// MARK: Instance
	/**
	 * Single-use query iteration.
	 *
	 * @author dereekb
	 */
	public class IteratorInstance
	        implements IndexedIterator<T>, LimitedIterator<T> {

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

		private int iteratorLimit;
		private final IterableObjectifyQueryInitializer<T> initializer;
		private final ObjectifyKeyedQuery<T> query;

		/**
		 * Custom, optional parameters to feed to {@link #initializer}.
		 */
		private Map<String, String> parameters;

		private IteratorInstance() {
			this.iteratorLimit = IterableObjectifyQuery.this.iterateLimit;
			this.initializer = IterableObjectifyQuery.this.initializer;
			this.query = IterableObjectifyQuery.this.query;
		}

		public IterableObjectifyQueryInitializer<T> getInitializer() {
			return this.initializer;
		}

		public ObjectifyKeyedQuery<T> getQuery() {
			return this.query;
		}

		public Map<String, String> getParameters() {
			return this.parameters;
		}

		public void setParameters(Map<String, String> parameters) {
			this.parameters = parameters;
		}

		// MARK: LimitedIterator
		@Override
		public int getIteratorLimit() {
			return this.iteratorLimit;
		}

		@Override
		public void setIteratorLimit(int iteratorLimit) throws IllegalArgumentException {
			if (iteratorLimit < 1 || iteratorLimit > MAX_LIMIT) {
				throw new IllegalArgumentException("Iterate limit restricted to between 1 and " + MAX_LIMIT + ".");
			}

			ArrayList<Integer> test = new ArrayList<>();
			test.iterator();

			this.iteratorLimit = iteratorLimit;
		}

		// MARK: IndexedIterator
		@Override
        public boolean hasNext() {
			QueryResultIterator<T> iterator = this.getIterator();
			boolean hasNext = iterator.hasNext();

			if (hasNext == false) {
				if (this.iteratorIndex >= this.iteratorLimit) { // Need to
																// retrieve next
												  // iterator
					iterator = this.getNextIterator();
					hasNext = iterator.hasNext();
					this.iteratorIndex = 0;
				}

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
		}

		@Override
		public void remove() {
			// Does nothing. Can choose to extend this class to allow removal
			// through a delegate, however.
		}

		@Override
		public T next() {
			boolean hasNext = this.hasNext();
			T next = null;

			if (hasNext) {
				next = this.iterator.next();
				this.iteratorIndex += 1;
			}

			return next;
		}

		protected ObjectifyQuery<T> newQuery() {
			ObjectifyQuery<T> query = this.query.defaultQuery();

			if (this.initializer != null) {
				this.initializer.initializeQuery(query, this.parameters);
			}

			return query;
		}

		private QueryResultIterator<T> continueQuery() {
			ObjectifyQuery<T> query = this.newQuery();
			query.setLimit(this.iteratorLimit);

			if (this.iteratorCursor != null) {
				query.setCursor(this.iteratorCursor);
			} else if (this.startCursor != null) {
				query.setCursor(this.startCursor);
			}

			QueryResultIterator<T> iterator = this.query.queryIterator(query);
			return iterator;
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

		public Cursor getStartCursor() {
			return this.startCursor;
		}

		public Cursor getCurrentCursor() {
			Cursor cursor = null;

			if (this.iterator != null) {
				cursor = this.iterator.getCursor();
			}

			return cursor;
		}

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

}
