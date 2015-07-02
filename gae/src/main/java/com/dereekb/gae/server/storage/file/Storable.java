package com.dereekb.gae.server.storage.file;

/**
 * Interface for items that can be stored.
 * 
 * @author dereekb
 */
public interface Storable {

	/**
	 * Returns the filename for this item.
	 */
	public String getFilename();

}
