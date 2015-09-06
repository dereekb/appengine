package com.dereekb.gae.server.storage.upload.deprecated;

import com.dereekb.gae.server.storage.file.StorableFileInfo;

/**
 * Represents an uploaded file that has bytes.
 *
 * @author dereekb
 */
@Deprecated
public interface UploadedFile
        extends StorableFileInfo {

	public byte[] getBytes();

	public void setBytes(byte[] bytes);

}
