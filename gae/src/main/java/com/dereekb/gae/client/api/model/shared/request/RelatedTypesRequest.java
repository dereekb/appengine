package com.dereekb.gae.client.api.model.shared.request;

import java.util.Set;

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

	/**
	 * Returns the list of types to load.
	 * 
	 * @return {@link Set}, or {@code null} if no filter specified.
	 */
	public Set<String> getRelatedTypesFilter();

	/**
	 * Sets the list of types to load.
	 * 
	 * @param types
	 *            {@link Set}, or {@code null} if no filtering needed.
	 */
	public void setRelatedTypesFilter(Set<String> types);

}
