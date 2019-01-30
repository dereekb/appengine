package com.dereekb.gae.server.datastore.models.dto;

/**
 * Interface for getting and setting a simple string key.
 * <p>
 * Used for external models.
 *
 * @author dereekb
 *
 */
public interface SimpleKeyModel {

	public String getKey();

	public void setKey(String key);

}
