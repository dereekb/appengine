package com.dereekb.gae.server.storage.accessor;

import java.io.IOException;

import com.dereekb.gae.server.deprecated.storage.object.file.StorableContent;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFile;

/**
 * Used to write and delete {@link StorableContent} from storage.
 *
 * @author dereekb
 *
 */
public interface StorageWriter {

	/**
	 * Saves the {@link StorableContent}.
	 *
	 * @param file
	 *            {@link StorableContent}. Never {@code null}.
	 * @throws IOException
	 *             thrown if the file fails being saved.
	 */
	public void saveFile(StorableContent file) throws IOException;

	/**
	 * Deletes any data associated with the {@link StorableFile}.
	 * <p>
	 * If the file doesn't exist, this function will not do anything and return
	 * {@code false}.
	 * </p>
	 *
	 * @param file
	 *            {@link StorableContent}. Never {@code null}.
	 * @return {@code true} if the file was found and deleted. {@code false} if
	 *         it didn't exist.
	 * @throws IOException
	 *             thrown if the file fails being deleted, but only if it
	 *             exists.
	 */
	public boolean deleteFile(StorableFile file) throws IOException;

}
