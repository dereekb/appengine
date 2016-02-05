package com.dereekb.gae.server.datastore.objectify.query.builder.impl;

import com.dereekb.gae.server.datastore.objectify.query.ObjectifyQueryRequestLimitedBuilder;
import com.dereekb.gae.server.datastore.objectify.query.builder.ObjectifyQueryRequestLimitedConfigurer;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyConditionQueryFilter;
import com.dereekb.gae.server.datastore.objectify.query.impl.ObjectifyQueryConditionOperator;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.impl.ObjectifyQueryOrderingImpl;

/**
 * {@link ObjectifyQueryRequestLimitedConfigurer} implementation that targets a
 * specific field.
 * <p>
 * Can specify an operator, and an ordering.
 *
 * @author dereekb
 *
 */
public class QueryFieldFilterConfigurer
        implements ObjectifyQueryRequestLimitedConfigurer {

	private String field;
	private ObjectifyQueryConditionOperator operator;
	private Object value;

	private ObjectifyQueryResultsOrdering ordering;

	public QueryFieldFilterConfigurer() {}

	public QueryFieldFilterConfigurer(String field, Object value) {
		this.setEqualityFilter(field, value);
	}

	public QueryFieldFilterConfigurer(String field, ObjectifyQueryConditionOperator operator, Object value) {
		this.setFilter(field, operator, value);
	}

	public void setComparison(ObjectifyQueryConditionOperator operator,
	                           Object value) {
		this.setOperator(operator);
		this.setValue(value);
	}

	public ObjectifyQueryConditionOperator getOperator() {
		return this.operator;
	}

	public void setOperator(ObjectifyQueryConditionOperator operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return this.value;
	}

	public QueryFieldFilterConfigurer setValue(Object value) {
		this.value = value;
		return this;
	}

	public QueryFieldFilterConfigurer setOrdering(ObjectifyQueryResultsOrdering ordering) {
		this.ordering = ordering;
		return this;
	}

	public String getField() {
		return this.field;
	}

	public QueryFieldFilterConfigurer setField(String field) {
		if (field == null || field.isEmpty()) {
			throw new IllegalArgumentException("Field cannot be a null or empty string.");
		}

		this.field = field;
		return this;
	}

	// Filter
	public QueryFieldFilterConfigurer setEqualityFilter(String field,
	                                                    Object value) {
		return this.setFilter(field, ObjectifyQueryConditionOperator.Equal, value);
	}

	public QueryFieldFilterConfigurer setFilter(String field,
	                                         ObjectifyQueryConditionOperator operator,
	                                         Object value) {
		this.setField(field);
		this.setOperator(operator);
		this.setValue(value);
		return this;
	}

	// MARK: ObjectifyQueryRequestConfigurer
	@Override
	public void configure(ObjectifyQueryRequestLimitedBuilder request) {
		if (this.field != null) {

			if (this.operator != null) {
				ObjectifyConditionQueryFilter filter = new ObjectifyConditionQueryFilter(this.field, this.operator,
				        this.value);
				request.addQueryFilter(filter);
			}

			if (this.ordering != null) {
				ObjectifyQueryOrdering ordering = new ObjectifyQueryOrderingImpl(this.field, this.ordering);
				request.addResultsOrdering(ordering);
			}

		}
	}

	@Override
	public String toString() {
		return "QueryFieldFilterConfigurer [field=" + this.field + ", operator=" + this.operator + ", value="
		        + this.value + ", ordering=" + this.ordering + "]";
	}

}
