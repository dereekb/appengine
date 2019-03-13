package com.dereekb.gae.server.datastore.models.query;

/**
 * Generic query request configuration.
 * <p>
 * Parameters used to configure this request, if applicable, are not exposed.
 *
 * @author dereekb
 *
 */
public interface IndexedModelQueryRequest {

	/**
	 * @return {@link IndexedModelQueryRequestOptions}. Never {@code null}.
	 */
	public IndexedModelQueryRequestOptions getOptions();

}
