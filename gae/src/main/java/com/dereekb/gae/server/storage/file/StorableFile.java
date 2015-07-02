package com.dereekb.gae.server.storage.file;

/**
 * Interface that extends the {@link Storable} interface and also contains the
 * full filepath to where it should be stored at.
 *
 * @author dereekb
 */
public interface StorableFile
        extends Storable {

	/**
	 * Returns the file path of this object.
	 *
	 * This is the full path that includes the filename.
	 */
	public String getFilePath();

}
