package com.dereekb.gae.server.datastore.models.comparison;


public interface ContentEquality {

	/**
	 * Checks whether two model's content are equivalent or not.
	 *
	 * Should not include identifier and other supporting values, but only the
	 * main values that make up the model.
	 *
	 * @param object
	 * @return
	 */
	public boolean contentEquals(Object object);

}
