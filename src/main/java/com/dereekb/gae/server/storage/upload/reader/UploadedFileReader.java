package com.dereekb.gae.server.storage.upload.reader;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author dereekb
 *
 */
public interface UploadedFileReader {

	public UploadedFileSet readUploadedFiles(HttpServletRequest request);

}
