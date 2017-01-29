package com.dereekb.gae.server.datastore.models.owner;

/**
 * Model that has an owner ID attached to it.
 * 
 * @author dereekb
 *
 */
public interface OwnedModel {

	/**
	 * Returns the owner id.
	 * 
	 * @return {@link String}. Can be {@code null}.
	 */
	public String getOwnerId();

}
