package com.dereekb.gae.server.datastore.models.query;

/**
 * Limited version of a {@link IndexedModelQueryRequestBuilder}.
 *
 * @author dereekb
 *
 */
public interface IndexedModelQueryRequestLimitedBuilder {

	/**
	 * Sets any builder options.
	 *
	 * @param options
	 *            {@link IndexedModelQueryRequestOptions}, or {@code null}.
	 */
	public void setOptions(IndexedModelQueryRequestOptions options);

}
