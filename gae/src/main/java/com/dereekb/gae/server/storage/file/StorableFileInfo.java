package com.dereekb.gae.server.storage.file;

import java.util.Date;

/**
 * Contains meta-data about the given file.
 *
 * @author dereekb
 *
 */
public interface StorableFileInfo {

	public String getFilename();

	public String getContentType();

	public Date getCreationDate();

	public Long getFileSize();

}
