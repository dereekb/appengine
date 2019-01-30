package com.dereekb.gae.server.datastore.models.owner;

/**
 * {@link OwnedModel} that can have its owner id changed.
 * 
 * @author dereekb
 *
 */
public interface MutableOwnedModel
        extends OwnedModel {

	/**
	 * Sets the owner id.
	 * 
	 * @param ownerKey
	 *            {@link String}. Can be {@code null}.
	 */
	public void setOwnerId(String ownerKey);

}
