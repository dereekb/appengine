package com.dereekb.gae.server.storage.upload.data;

import com.dereekb.gae.server.deprecated.storage.object.file.StorableContent;
import com.dereekb.gae.server.deprecated.storage.object.file.StorableFileInfo;

/**
 * Used to copy {@code byte[]} data.
 *
 * @author dereekb
 *
 */
public interface BytesContentCopier {

	/**
	 * Copies the input bytes.
	 *
	 * @param bytes
	 *            Bytes to copy. Never {@code null}.
	 * @param info
	 *            Optional {@link StorableFileInfo} to use.
	 *
	 * @return {@code StorableContent} for any file that may have been created.
	 *         If no file was created, this will return {@code null}.
	 */
	public StorableContent copyData(byte[] bytes,
	                     StorableFileInfo info);

}
