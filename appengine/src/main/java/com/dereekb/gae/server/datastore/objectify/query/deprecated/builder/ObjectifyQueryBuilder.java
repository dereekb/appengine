package com.dereekb.gae.server.datastore.objectify.query.builder;

import java.util.List;

import com.dereekb.gae.server.datastore.objectify.query.ConfiguredObjectifyQuery;
import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ExpressionOperator;
import com.dereekb.gae.utilities.factory.Factory;
import com.dereekb.gae.utilities.factory.exception.FactoryMakeFailureException;

/**
 * Utility class to help generate an ObjectifyQuery<T>.
 *
 * @author dereekb
 *
 * @param <T>
 */
@Deprecated
public class ObjectifyQueryBuilder<T>
        implements Factory<ConfiguredObjectifyQuery<T>> {

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

	public ConfiguredObjectifyQuery<T> fieldEqualsQuery(String field,
	                                          Object value) {
		return this.fieldQuery(field, ExpressionOperator.Equal, value, QueryOrdering.Ascending);
	}

	public ConfiguredObjectifyQuery<T> fieldQuery(String field,
	                                    ExpressionOperator operator,
	                                    Object value) {
		return this.fieldQuery(field, operator, value, QueryOrdering.Ascending);
	}

	public ConfiguredObjectifyQuery<T> fieldQuery(String field,
	                                    ExpressionOperator operator,
	                                    Object value,
	                                    QueryOrdering ordering) {
		ConfiguredObjectifyQuery<T> query = this.make();
		List<ObjectifyQueryFilter> filters = query.getQueryFilters();

		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		filters.add(fieldFilter);

		ObjectifyQueryOrderingImpl valueOrdering = new ObjectifyQueryOrderingImpl(field, ordering);
		query.setResultsOrdering(valueOrdering);

		return query;
	}

	public ObjectifyConditionQueryFilter fieldFilter(String field,
	                                                 ExpressionOperator operator,
	                                                 Object value) {
		ObjectifyConditionQueryFilter fieldFilter = new ObjectifyConditionQueryFilter(field, operator, value);
		return fieldFilter;
	}

	@Override
	public ConfiguredObjectifyQuery<T> make() throws FactoryMakeFailureException {
		ConfiguredObjectifyQuery<T> query = new ConfiguredObjectifyQuery<T>(this.type);

		query.setLimit(this.limit);

		return query;
	}

}
