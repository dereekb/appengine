package com.dereekb.gae.extras.gen.utility;

import java.io.IOException;

/**
 * Used for writing {@link GenFolder} and {@link GenFile} values to the disk.
 *
 * @author dereekb
 *
 */
public interface GenFilesWriter {

	/**
	 * Writes the input folder (and contained files within the folder).
	 *
	 * @param folder
	 * @throws IOException
	 */
	public void writeFiles(GenFolder folder) throws IOException;

	/**
	 * Writes only the files within the folder.
	 *
	 * @param folder
	 * @throws IOException
	 */
	public void writeFilesInFolder(GenFolder folder) throws IOException;

}
