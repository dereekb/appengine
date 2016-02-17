package com.dereekb.gae.server.datastore.objectify.query.order.impl;

import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryOrdering;
import com.dereekb.gae.server.datastore.objectify.query.order.ObjectifyQueryResultsOrdering;


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

	private String variable;
	private ObjectifyQueryResultsOrdering ordering;

	public ObjectifyQueryOrderingImpl(ObjectifyQueryResultsOrdering ordering) {
		this(ORDER_BY_KEY_VALUE, ordering);
	}

	public ObjectifyQueryOrderingImpl(String variable, ObjectifyQueryResultsOrdering ordering) {
		this.setVariable(variable);
		this.setOrdering(ordering);
	}

	public static ObjectifyQueryOrderingImpl orderByKey(ObjectifyQueryResultsOrdering ordering) {
		if (ordering == null) {
			ordering = ObjectifyQueryResultsOrdering.Descending;
		}

		return new ObjectifyQueryOrderingImpl(ordering);
	}

	public String getVariable() {
		return this.variable;
	}

	public void setVariableAsKeysOrdering() {
		this.setVariable(ORDER_BY_KEY_VALUE);
	}

	public void setVariable(String variable) {
		if (variable == null || variable.isEmpty()) {
			throw new IllegalArgumentException("Variable cannot be a null or empty string.");
		}

		this.variable = variable;
	}

	public ObjectifyQueryResultsOrdering getOrdering() {
		return this.ordering;
	}

	public void setOrdering(ObjectifyQueryResultsOrdering ordering) {
		if (ordering == null) {
			throw new IllegalArgumentException("Ordering cannot be null.");
		}

		this.ordering = ordering;
	}

	public boolean isAscending() {
		return (this.ordering == ObjectifyQueryResultsOrdering.Ascending);
	}

	public boolean isDescending() {
		return (this.ordering == ObjectifyQueryResultsOrdering.Descending);
	}

    public boolean isKeysOrdering() {
		return this.variable.equals(ORDER_BY_KEY_VALUE);
	}

	@Override
	public String getOrderingString() {
		String condition = this.variable;

		if (this.ordering == ObjectifyQueryResultsOrdering.Descending) {
			condition = "-" + condition;
		}

		return condition;
	}

}
