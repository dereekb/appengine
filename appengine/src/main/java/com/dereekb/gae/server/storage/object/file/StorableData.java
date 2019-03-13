package com.dereekb.gae.server.storage.object.file;

/**
 * {@link StorableFile} that also contains data.
 *
 * @author dereekb
 *
 */
public interface StorableData
        extends StorableFile {

	/**
	 * File data. Never {@code null}.
	 *
	 * @return {@link byte} array.
	 */
	public byte[] getFileData();

}
