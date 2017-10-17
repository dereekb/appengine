package com.dereekb.gae.server.datastore.objectify.query.order.impl;

import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.utilities.query.order.QueryResultsOrdering;


/**
 * Ordering pair.
 *
 * @author dereekb
 *
 * @see ObjectifyQueryOrdering
 */
public class ObjectifyQueryOrderingImpl
        implements ObjectifyQueryOrdering {

	public static final String ORDER_BY_KEY_VALUE = "__key__";

	private String field;
	private QueryResultsOrdering ordering;

	public ObjectifyQueryOrderingImpl(QueryResultsOrdering ordering) {
		this(ORDER_BY_KEY_VALUE, ordering);
	}

	public ObjectifyQueryOrderingImpl(String field, QueryResultsOrdering ordering) {
		this.setField(field);
		this.setOrdering(ordering);
	}

	public static ObjectifyQueryOrderingImpl orderByKey(QueryResultsOrdering ordering) {
		if (ordering == null) {
			ordering = QueryResultsOrdering.Descending;
		}

		return new ObjectifyQueryOrderingImpl(ordering);
	}

	@Override
	public String getField() {
		return this.field;
	}

	public void setFieldAsKeysOrdering() {
		this.setField(ORDER_BY_KEY_VALUE);
	}

	public void setField(String variable) {
		if (variable == null || variable.isEmpty()) {
			throw new IllegalArgumentException("Field cannot be a null or empty string.");
		}

		this.field = variable;
	}

	public QueryResultsOrdering getOrdering() {
		return this.ordering;
	}

	public void setOrdering(QueryResultsOrdering ordering) {
		if (ordering == null) {
			throw new IllegalArgumentException("Ordering cannot be null.");
		}

		this.ordering = ordering;
	}

	public boolean isAscending() {
		return (this.ordering == QueryResultsOrdering.Ascending);
	}

	public boolean isDescending() {
		return (this.ordering == QueryResultsOrdering.Descending);
	}

    public boolean isKeysOrdering() {
		return this.field.equals(ORDER_BY_KEY_VALUE);
	}

	@Override
	public String getOrderingString() {
		String condition = this.field;

		if (this.ordering == QueryResultsOrdering.Descending) {
			condition = "-" + condition;
		}

		return condition;
	}

	@Override
	public String toString() {
		return "ObjectifyQueryOrderingImpl [field=" + this.field + ", ordering=" + this.ordering + "]";
	}

}
