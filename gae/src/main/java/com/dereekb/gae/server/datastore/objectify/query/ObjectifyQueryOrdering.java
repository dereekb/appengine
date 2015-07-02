package com.dereekb.gae.server.datastore.objectify.query;

import com.dereekb.gae.utilities.collections.chain.Chain;

/**
 * Defines the order in which objects are returned. Can chain multiple ObjectifyQueryOrderings together.
 * 
 * @author dereekb
 */
public class ObjectifyQueryOrdering extends Chain<ObjectifyQueryOrdering> {

	public static enum QueryOrdering {
		/**
		 * Orders from lowest value to the highest value
		 */
		Ascending,

		/**
		 * Orders from the highest value to the lowest value.
		 */
		Descending
	}

	public static final String ORDER_BY_KEY_VALUE = "__key__";

	private final QueryOrdering ordering;
	private final String variable;

	public ObjectifyQueryOrdering() {
		this(ORDER_BY_KEY_VALUE);
	}

	public ObjectifyQueryOrdering(String variable) throws NullPointerException, IllegalArgumentException {
		this(variable, QueryOrdering.Descending);
	}

	public ObjectifyQueryOrdering(String variable, QueryOrdering ordering) throws NullPointerException,
	        IllegalArgumentException {
		if (variable == null) {
			throw new NullPointerException("Variable cannot be null.");
		}

		if (variable.isEmpty()) {
			throw new IllegalArgumentException("Variable cannot be an empty string.");
		}

		if (ordering == null) {
			throw new NullPointerException("Ordering cannot be null.");
		}

		this.variable = variable;
		this.ordering = ordering;
	}

	public ObjectifyQueryOrdering chain(String variable) throws NullPointerException, IllegalArgumentException {
		ObjectifyQueryOrdering queryOrdering = new ObjectifyQueryOrdering(variable);
		return this.chain(queryOrdering);
	}

	public ObjectifyQueryOrdering chain(String variable,
	                                    QueryOrdering ordering) throws NullPointerException, IllegalArgumentException {
		ObjectifyQueryOrdering queryOrdering = new ObjectifyQueryOrdering(variable, ordering);
		return this.chain(queryOrdering);
	}

	public boolean isAscending() {
		return (this.ordering == QueryOrdering.Ascending);
	}

	public boolean isDescending() {
		return (this.ordering == QueryOrdering.Descending);
	}

	public boolean isKeysOrdering() {
		return this.variable.equals(ORDER_BY_KEY_VALUE);
	}

	@Override
	public String toString() {
		String condition = this.variable;

		if (this.ordering == QueryOrdering.Descending) {
			condition = "-" + condition;
		}

		return condition;
	}

	/**
	 * Creates a new ordering that uses a model's key.
	 * 
	 * @param ordering Ascending or Descending order. If null, defaults to Descending order.
	 * @return new ObjectifyQueryOrdering instance
	 */
	public static ObjectifyQueryOrdering orderByKey(QueryOrdering ordering) {
		if (ordering == null) {
			ordering = QueryOrdering.Descending;
		}

		ObjectifyQueryOrdering queryOrdering = new ObjectifyQueryOrdering(ORDER_BY_KEY_VALUE, ordering);
		return queryOrdering;
	}

	public QueryOrdering getOrdering() {
		return ordering;
	}

	public String getVariable() {
		return variable;
	}

	@Override
	protected ObjectifyQueryOrdering self() {
		return this;
	}

}
