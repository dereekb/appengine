package com.dereekb.gae.server.storage.file;

/**
 * Extension of {@link Storable} that defines not only the filename, but also
 * the file's full path.
 *
 * @author dereekb
 */
public interface StorableFile
        extends Storable {

	/**
	 * Returns the full <i>file</i> path of this object.
	 *
	 * This is the full path that includes the filename.
	 */
	public String getFilePath();

}
