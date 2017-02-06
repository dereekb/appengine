package com.dereekb.gae.server.storage.object.file;

/**
 * Item that can be stored. Contains only a filename,
 *
 * @author dereekb
 * @see {@link StorableFile}
 */
public interface Storable {

	/**
	 * Returns the filename for this item.
	 */
	public String getFilename();

}
