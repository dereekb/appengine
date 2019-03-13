package com.dereekb.gae.server.datastore.objectify.query.iterator.impl;

import java.util.Map;

import com.dereekb.gae.server.datastore.models.query.IndexedModelQueryModelResultIterator;
import com.dereekb.gae.server.datastore.models.query.iterator.IndexedModelQueryIterable;
import com.dereekb.gae.server.datastore.models.query.iterator.impl.IndexedModelQueryIterableFactoryImpl;
import com.dereekb.gae.server.datastore.objectify.ObjectifyModel;
import com.dereekb.gae.server.datastore.objectify.components.query.ObjectifyQueryService;
import com.dereekb.gae.server.datastore.objectify.query.ExecutableObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilder;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestBuilderFactory;
import com.dereekb.gae.server.datastore.objectify.query.cursor.impl.ObjectifyCursor;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryModelResultIteratorImpl;
import com.dereekb.gae.server.datastore.objectify.query.iterator.ObjectifyQueryIterableFactory;
import com.dereekb.gae.utilities.collections.iterator.cursor.ResultsCursor;
import com.googlecode.objectify.cmd.SimpleQuery;

/**
 * {@link ObjectifyQueryIterableFactory} implementation using a {@link ObjectifyQueryService},
 * which is used to iterate over models in the database.
 *
 * @author dereekb
 *
 * @param <T>
 *            model type
 */
public class ObjectifyQueryIterableFactoryImpl<T extends ObjectifyModel<T>> extends IndexedModelQueryIterableFactoryImpl<T>
        implements ObjectifyQueryIterableFactory<T> {

	public static final Integer MIN_CHUNK_SIZE = 10;

	/**
	 * Overrides the default chunk size used by queries. Use to tune
	 * performance.
	 */
	private Integer chunkSize = null;

	public ObjectifyQueryIterableFactoryImpl(ObjectifyQueryRequestBuilderFactory<T> queryBuilderFactory) {
		super(queryBuilderFactory);
	}

	@Override
	public ObjectifyQueryRequestBuilderFactory<T> getQueryBuilderFactory() {
		return (ObjectifyQueryRequestBuilderFactory<T>) super.getQueryBuilderFactory();
	}

	public Integer getChunkSize() {
		return this.chunkSize;
	}

	public void setChunkSize(Integer chunkSize) {
		if (chunkSize != null && (chunkSize < MIN_CHUNK_SIZE || chunkSize > IndexedModelQueryIterableFactoryImpl.MAX_ITERATION_LIMIT)) {
			throw new IllegalArgumentException(
			        "Iterate limit restricted to between " + MIN_CHUNK_SIZE + " and " + IndexedModelQueryIterableFactoryImpl.MAX_ITERATION_LIMIT + ".");
		}

		this.chunkSize = chunkSize;
	}

	// MARK: ObjectifyQueryIterableFactory
	@Override
	public IterableInstance makeIterable() {
		return new IterableInstance();
	}

	@Override
	public IterableInstance makeIterable(ObjectifyCursor startCursor) {
		return new ObjectifyIterableInstance(startCursor);
	}

	@Override
	public IterableInstance makeIterable(Map<String, String> parameters,
	                                     ObjectifyCursor startCursor) {
		return new ObjectifyIterableInstance(parameters, startCursor);
	}

	@Override
	public IndexedModelQueryIterable<T> makeIterable(SimpleQuery<T> query) {
		return new ObjectifyIterableInstance(query, null);
	}

	@Override
	public IndexedModelQueryIterable<T> makeIterable(SimpleQuery<T> query,
	                                                 ObjectifyCursor cursor) {
		return new ObjectifyIterableInstance(query, cursor);
	}

	// MARK: Internal Classes
	public class ObjectifyIterableInstance extends IterableInstance {

		private SimpleQuery<T> simpleQuery;

		public ObjectifyIterableInstance() {
			super();
		}

		public ObjectifyIterableInstance(SimpleQuery<T> simpleQuery, ResultsCursor startCursor) {
			super(startCursor);
			this.simpleQuery = simpleQuery;
		}

		public ObjectifyIterableInstance(Map<String, String> parameters, ResultsCursor startCursor) {
			super(parameters, startCursor);
		}

		public ObjectifyIterableInstance(ResultsCursor startCursor) {
			super(startCursor);
		}

		// MARK: IndexedIterable
		@Override
		public IteratorInstance iterator() {
			if (this.simpleQuery != null) {
				return new ObjectifyIteratorInstance(this.getStartCursor(), this.simpleQuery);
			} else {
				return new ObjectifyIteratorInstance(this.getStartCursor(), this.getParameters());
			}
		}

	}

	// MARK: Instance
	private final class ObjectifyIteratorInstance extends IteratorInstance {

		private SimpleQuery<T> query;

		private ObjectifyIteratorInstance(ResultsCursor startCursor, SimpleQuery<T> query) {
			super(startCursor);
			this.setQuery(query);
		}

		private ObjectifyIteratorInstance(ResultsCursor startCursor, Map<String, String> parameters) {
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
			if (ObjectifyQueryIterableFactoryImpl.this.chunkSize != null) {
				query = query.chunk(ObjectifyQueryIterableFactoryImpl.this.chunkSize);
			}

			this.query = query;
		}

		protected SimpleQuery<T> makeQuery(Map<String, String> parameters) {
			ObjectifyQueryRequestBuilder<T> builder = getQueryBuilderFactory().makeQuery(parameters);
			ExecutableObjectifyQuery<T> query = builder.buildExecutableQuery();
			return query.getQuery();
		}

		// MARK: AbstractGenericIteratorInstance
		@Override
		protected IndexedModelQueryModelResultIterator<T> continueQuery() {
			SimpleQuery<T> query = this.query;
			ResultsCursor iteratorCursor = this.getIteratorCursor();

			if (iteratorCursor != null) {
				query = query.startAt(ObjectifyCursor.make(iteratorCursor).getCursor());
			}

			query = query.limit(this.getIteratorBatchLimit());

			if (ObjectifyQueryIterableFactoryImpl.this.chunkSize != null) {
				query = query.chunk(ObjectifyQueryIterableFactoryImpl.this.chunkSize);
			}

			return new ObjectifyQueryModelResultIteratorImpl<T>(query.iterator());
		}

	}

	@Override
	public String toString() {
		return "ObjectifyQueryIterableFactoryImpl [chunkSize=" + this.chunkSize + ", getQueryBuilderFactory()="
		        + this.getQueryBuilderFactory() + ", getIterateLimit()=" + this.getIterateLimit() + "]";
	}

}
