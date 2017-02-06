package com.dereekb.gae.utilities.query.order;

/**
 * Which way to sort index results.
 * 
 * @author dereekb
 *
 */
public enum QueryResultsOrdering {

	/**
	 * Orders from lowest value to the highest value
	 */
	Ascending("ASC"),

	/**
	 * Orders from the highest value to the lowest value.
	 */
	Descending("DESC");

	private final String code;

	private QueryResultsOrdering(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static QueryResultsOrdering fromString(String string) throws IllegalArgumentException {
		QueryResultsOrdering order;

		switch (string) {
			case "ASC":
				order = QueryResultsOrdering.Ascending;
				break;
			case "DESC":
				order = QueryResultsOrdering.Descending;
				break;
			default:
				throw new IllegalArgumentException();
		}

		return order;
	}

}
