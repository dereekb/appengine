package com.dereekb.gae.server.storage.upload.reader;

import com.dereekb.gae.server.storage.object.file.StorableFileInfo;

public interface UploadedFileInfo
        extends StorableFileInfo {

	public String getMd5Hash();

}
