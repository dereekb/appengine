package com.dereekb.gae.server.datastore.objectify.query.order;



public enum ObjectifyQueryResultsOrdering {

	/**
	 * Orders from lowest value to the highest value
	 */
	Ascending("ASC"),

	/**
	 * Orders from the highest value to the lowest value.
	 */
	Descending("DESC");

	private final String code;

	private ObjectifyQueryResultsOrdering(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public static ObjectifyQueryResultsOrdering fromString(String string) throws IllegalArgumentException {
		ObjectifyQueryResultsOrdering order;

		switch (string) {
			case "ASC":
				order = ObjectifyQueryResultsOrdering.Ascending;
				break;
			case "DESC":
				order = ObjectifyQueryResultsOrdering.Descending;
				break;
			default:
				throw new IllegalArgumentException();
		}

		return order;
	}

}
