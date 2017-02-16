package com.dereekb.gae.client.api.model.shared.request;

/**
 * Request that controls whether or not related types
 * should be loaded.
 * 
 * @author dereekb
 *
 */
public interface RelatedTypesRequest {

	/**
	 * Whether or not to retrieve related types.
	 * 
	 * @return {@code true} if the response should contain related types.
	 */
	public boolean shouldLoadRelatedTypes();

}
