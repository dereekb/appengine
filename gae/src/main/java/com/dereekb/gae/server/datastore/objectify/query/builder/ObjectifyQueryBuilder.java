package com.dereekb.gae.server.datastore.objectify.query.builder;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilterOperator;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryOrdering.QueryOrdering;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.FactoryMakeFailureException;

/**
 * Utility class to help generate an ObjectifyQuery<T>.
 *
 * @author dereekb
 *
 * @param <T>
 */
public class ObjectifyQueryBuilder<T>
        implements Factory<ObjectifyQuery<T>> {

	public static final Integer DEFAULT_LIMIT = 20;

	protected final Class<T> type;

	protected Integer limit;

	public ObjectifyQueryBuilder(Class<T> type) {
		this(type, DEFAULT_LIMIT);
	}

	public ObjectifyQueryBuilder(Class<T> type, Integer limit) {
		this.type = type;
		this.limit = limit;
	}

	public Class<T> getType() {
		return this.type;
	}

	public Integer getLimit() {
		return this.limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public ObjectifyQuery<T> fieldEqualsQuery(String field,
	                                          Object value) {
		return this.fieldQuery(field, ObjectifyQueryFilterOperator.Equal, value, QueryOrdering.Ascending);
	}

	public ObjectifyQuery<T> fieldQuery(String field,
	                                    ObjectifyQueryFilterOperator operator,
	                                    Object value) {
		return this.fieldQuery(field, operator, value, QueryOrdering.Ascending);
	}

	public ObjectifyQuery<T> fieldQuery(String field,
	                                    ObjectifyQueryFilterOperator operator,
	                                    Object value,
	                                    QueryOrdering ordering) {
		ObjectifyQuery<T> query = this.make();
		List<ObjectifyQueryFilter> filters = query.getQueryFilters();

		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		filters.add(fieldFilter);

		ObjectifyQueryOrdering valueOrdering = new ObjectifyQueryOrdering(field, ordering);
		query.setResultsOrdering(valueOrdering);

		return query;
	}

	public ObjectifyConditionQueryFilter fieldFilter(String field,
	                                                 ObjectifyQueryFilterOperator operator,
	                                                 Object value) {
		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		return fieldFilter;
	}

	@Override
	public ObjectifyQuery<T> make() throws FactoryMakeFailureException {
		ObjectifyQuery<T> query = new ObjectifyQuery<T>(this.type);

		query.setLimit(this.limit);

		return query;
	}

}
