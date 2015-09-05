package com.dereekb.gae.server.storage.upload.deprecated;

import com.dereekb.gae.server.storage.file.StorageFileInfo;

/**
 * Represents an uploaded file that has bytes.
 *
 * @author dereekb
 */
@Deprecated
public interface UploadedFile
        extends StorageFileInfo {

	public byte[] getBytes();

	public void setBytes(byte[] bytes);

}
