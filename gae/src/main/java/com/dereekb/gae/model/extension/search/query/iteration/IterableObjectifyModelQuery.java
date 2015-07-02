package com.dereekb.gae.model.extension.search.query.iteration;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.query.IterableModelQuery;
import com.dereekb.gae.server.datastore.models.query.ModelQueryIterator;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.ObjectifyRegistry;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;

/**
 * An iterator that performs a query using an {@link ObjectifyQuery} then uses
 * the query results as a source.
 *
 * @author dereekb
 *
 * @param <T>
 * @param <K>
 */
public class IterableObjectifyModelQuery<T extends ObjectifyModel<T>>
        implements IterableModelQuery<T> {

	public static final Integer MAX_QUERY_LIMIT = 1000;

	private ObjectifyQueryInitializer<T> initializer;
	private ObjectifyRegistry<T> registry;

	/**
	 * Amount of elements to batch in a query. This is purely a performance
	 * setting, and is not a limit on the number of items that will be iterated
	 * in total.
	 */
	private Integer queryLimit = MAX_QUERY_LIMIT;

	/**
	 * Cursor to start queries at.
	 */
	private Cursor startCursor;

	@Override
	public ModelQueryIterator<T> make() throws FactoryMakeFailureException {
		return this.iterator();
	}

	@Override
	public ModelQueryIterator<T> iterator() {
		IteratorInstance instance = new IteratorInstance(this.startCursor,
 this.queryLimit, this.registry,
		        this.initializer);
		return instance;
	}

	private class IteratorInstance implements ModelQueryIterator<T> {

		private final Integer limit;
		private final ObjectifyQueryInitializer<T> initializer;
		private final ObjectifyRegistry<T> registry;

		private Integer current = 0;
		private QueryResultIterator<T> iterator;

		/**
		 * Cursor used by the iterator.
		 */
		private Cursor iteratorCursor;

		/**
		 * Cursor this iterator starts on.
		 */
		private final Cursor startCursor;

		/**
		 * Cursor this iterator ends on.
		 */
		private Cursor endCursor;

		private IteratorInstance(Cursor startCursor, Integer limit,
		        ObjectifyRegistry<T> registry,
		                         ObjectifyQueryInitializer<T> initializer) {
			this.startCursor = startCursor;
			this.limit = limit;
			this.registry = registry;
			this.initializer = initializer;
		}

		@Override
		public boolean hasNext() {
			QueryResultIterator<T> iterator = this.getIterator();
			boolean hasNext = iterator.hasNext();

			if (hasNext == false) {
				if (this.current >= this.limit) { // Need to retrieve next iterator
					iterator = this.getNextIterator();
					hasNext = iterator.hasNext();
					this.current = 0;
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
			//Does nothing. Can choose to extend this class to allow removal through a delegate, however.
		}

		@Override
		public T next() {
			boolean hasNext = this.hasNext();
			T next = null;

			if (hasNext) {
				next = this.iterator.next();
				this.current += 1;
			}

			return next;
		}

		protected ObjectifyQuery<T> newQuery() {
			ObjectifyQuery<T> query = this.registry.defaultQuery();

			if (this.initializer != null) {
				this.initializer.initialize(query);
			}

			return query;
		}

		private QueryResultIterator<T> continueQuery() {
			ObjectifyQuery<T> query = this.newQuery();
			query.setLimit(this.limit);

			if (this.iteratorCursor != null) {
				query.setCursor(this.iteratorCursor);
			} else if (this.startCursor != null) {
				query.setCursor(this.startCursor);
			}

			QueryResultIterator<T> iterator = this.registry.queryIterator(query);
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

		@Override
		public Cursor getCursor() {
			Cursor cursor = null;

			if (this.iterator != null) {
				cursor = this.iterator.getCursor();
			}

			return cursor;
		}

		@Override
		public Cursor getStartCursor() {
			return this.startCursor;
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

	}

	public ObjectifyRegistry<T> getRegistry() {
		return this.registry;
	}

	public void setRegistry(ObjectifyRegistry<T> registry) {
		this.registry = registry;
	}

	public ObjectifyQueryInitializer<T> getInitializer() {
		return this.initializer;
	}

	public void setInitializer(ObjectifyQueryInitializer<T> initializer) {
		this.initializer = initializer;
	}

	@Override
	public Cursor getStartCursor() {
		return this.startCursor;
	}

	@Override
	public void setStartCursor(Cursor startCursor) {
		this.startCursor = startCursor;
	}

	public Integer getQueryLimit() {
		return this.queryLimit;
	}

	public void setQueryLimit(Integer limit) throws NullPointerException,
	IllegalArgumentException {
		if (limit == null) {
			throw new NullPointerException("Limit cannot be null.");
		} else if (limit <= 0) {
			throw new IllegalArgumentException("Limit must be greater than 0.");
		}

		this.queryLimit = limit;
	}

	@Override
	public void setCustomParameters(Map<String, String> parameters) {
		if (this.initializer != null) {
			this.initializer.setParameters(parameters);
		}
	}

}
